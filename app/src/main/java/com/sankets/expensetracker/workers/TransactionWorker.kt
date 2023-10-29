package com.sankets.expensetracker.workers

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sankets.expensetracker.data.SMS
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.data.di.coroutine.CoroutineDispatcherProvider
import com.sankets.expensetracker.data.storage.StorageManager
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.domain.util.Constants.LOG_WORKER
import com.sankets.expensetracker.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.regex.Pattern
import kotlin.random.Random

@HiltWorker
class TransactionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val coroutineDispatcher: CoroutineDispatcherProvider,
    private val storageManager: StorageManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val newSmsReceived = inputData.getInt(Constants.NUMBER_OF_NEW_MESSAGES_RECIEVED, 0)
        getAllSMS(context, newSmsReceived).let { smsList -> // if we get the location
            return when (val result =
                getAllTransactionsOfBank(smsList.data)) {
                is Resource.Success -> {
                    result.data?.forEach { transaction ->
                        storageManager.insertTransactionIntoDB(transaction)
                    }
                    Timber.tag(LOG_WORKER).d("Worker Success : ${result.data}")
                    Result.success()
                }

                is Resource.Error -> { // to update ui
                    Timber.tag(LOG_WORKER).d("Worker Error : ${result.message}")
                    Result.retry()
                }

                else -> {Result.retry()}
            }
        }
    }

    private suspend fun getAllSMS(context: Context, newSmsReceived: Int): Resource<List<SMS>> =
        withContext(coroutineDispatcher.ioDispatcher) {


            val hasReadSmsPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED

            val hasReceiveSmsPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECEIVE_SMS
            ) == PackageManager.PERMISSION_GRANTED

            // if permissions are not provided
            if (!hasReadSmsPermission || !hasReceiveSmsPermission) {
//            FirebaseCrashlytics.getInstance().apply {
//                log("No Crash : Permissions Not Granted")
//            }
                return@withContext Resource.Error(
                    "No Crash : Permissions Not Granted"
                )
            }

            val lstSms: MutableList<SMS> = ArrayList()

            try {
                val message: Uri = Uri.parse("content://sms/")
                val cr: ContentResolver = context.contentResolver

                val cursor: Cursor? = cr.query(message, null, null, null, Telephony.Sms.DEFAULT_SORT_ORDER)

                val totalSMS: Int = cursor!!.count
                Timber.tag(LOG_WORKER).d("TOTAL SMS : $totalSMS, NEW SMS : $newSmsReceived")
                // TODO optimise this based on newSmsReceived
                if (cursor.moveToFirst()) {
                    for (i in 0 until totalSMS) {

                        val smsSource = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                        val smsBody = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                        val regEx =
                            Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}") // two digit - six digit
                        val matcher = regEx.matcher(smsSource)
                        val whichBank = Constants.BANK_TYPES.find { it in smsSource.uppercase() }
                        if (matcher.find() && !whichBank.isNullOrEmpty()) { // TO TEST ON DEVICE
//                    if(smsSource.equals("1234567890")){ // TO TEST ON EMULATOR

                            if (smsBody.contains("debited") || smsBody.contains("credited") || smsBody.contains(
                                    "received"
                                ) || smsBody.contains("purchase") || smsBody.contains("withdrawn")
                            ) {
                                val objectSMS = SMS(
                                    id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                                    source = smsSource,
                                    msgBody = smsBody,
                                    time = cursor.getLong(cursor.getColumnIndexOrThrow("date")),
                                    bankType = whichBank ?: ""
                                )

                                Timber.tag(LOG_WORKER).d("MESSAGE ID : ${objectSMS.id}")
                                lstSms.add(objectSMS)
                            }
                        } else {
                            Timber.tag(LOG_WORKER).d("getAllSMS: not found anything $smsSource $smsBody ")
//                        FirebaseCrashlytics.getInstance().apply {
//                            log("No Crash : getAllSMS: not found anything $smsSource $smsBody")
//                        }
                        }
                        cursor.moveToNext()
                    }
                }
                cursor.close()

                return@withContext Resource.Success(
                    data = lstSms.sortedByDescending { it.time }
                )

            } catch (exception: Exception) {
//            FirebaseCrashlytics.getInstance().apply {
//                log("No Crash : Exception ${exception.message}")
//            }
                return@withContext Resource.Error(
                    exception.message ?: "unknown Exception"
                )
            }

        }

    private suspend fun getAllTransactionsOfBank(
        messageList: List<SMS>?,
    ): Resource<List<Transaction>> = withContext(coroutineDispatcher.ioDispatcher) {
        val transactionList: MutableList<Transaction> = ArrayList()
        try {
            for (sms in messageList!!) {
                val body = sms.msgBody
                val bankType = sms.bankType
                val transaction = Transaction(
                    id = generateId(sms.id),
                    amount = getAmount(body),
                    isCredited = isCredited(body),
                    sourceName = sms.source,
                    date = sms.time,
                    bankName = sms.source.substring(2, 8),
                    msgBody = sms.msgBody,
                    bankType = bankType
                )
                var regex = ""
                if (bankType in Constants.BANK_TYPES && sms.bankType != Constants.ALL) regex = bankType

                if (transaction.sourceName.uppercase()
                        .contains(regex) || transaction.msgBody.uppercase()
                        .contains(regex) && transaction.msgBody.lowercase().contains("otp").not()
                ) {
                    Timber.tag(LOG_WORKER).d("adding into DB : $transaction")
                    transactionList.add(transaction)
                } else {

                }

            }
            return@withContext Resource.Success(data = transactionList)
        } catch (ex: Exception) {
//            FirebaseCrashlytics.getInstance().apply {
//                log("No Crash : Exception ${ex.message}")
//            }
            return@withContext Resource.Error(ex.message ?: "unknown Exception")
        }


    }

    private fun isCredited(messageBody: String): Boolean {
        return if (messageBody.contains("debited") ||
            messageBody.contains("purchasing") || messageBody.contains("purchase") || messageBody.contains(
                "dr"
            )
        ) {
            false
        } else messageBody.contains("credited") || messageBody.contains("cr")
    }


    private fun getAmount(messageBody: String): String {
        val regEx = Pattern.compile(Constants.AMOUNT)
        val matcher = regEx.matcher(messageBody)
        if (matcher.find()) {
            try {
                Timber.tag("AMOUNT").d("${matcher.group(0)}")
                return "" + matcher.group(0)

            } catch (e: Exception) {
                return ""
            }
        } else {
            return ""
        }
    }

    private fun generateId(smsId : String): Int {
        return try{
            Integer.parseInt(smsId)
        }
        catch (ex : Exception){
            Random.nextInt(1000000)
        }
    }


}