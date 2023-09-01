package com.sankets.expensetracker.domain.repository

import android.Manifest
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sankets.expensetracker.data.SMS
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.domain.util.Constants.AMOUNT
import com.sankets.expensetracker.domain.util.Constants.BANK_TYPES
import com.sankets.expensetracker.domain.util.Constants.MERCHANT
import com.sankets.expensetracker.domain.util.Resource
import java.util.regex.Pattern
import javax.inject.Inject


class TransactionRepoImpl @Inject constructor(
    private val application: Application
) : TransactionRepo {

    override suspend fun getAllSMS(context: Context): Resource<List<SMS>>? {

        val hasReadSmsPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED

        val hasReceiveSmsPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED

        // if permissions are not provided
        if (!hasReadSmsPermission || !hasReceiveSmsPermission) {
            FirebaseCrashlytics.getInstance().apply {
                log("No Crash : Permissions Not Granted")
            }
            return null
        }


        val lstSms: MutableList<SMS> = ArrayList()

        try {
            val message: Uri = Uri.parse("content://sms/")
            val cr: ContentResolver = context.contentResolver

            val cursor: Cursor? = cr.query(message, null, null, null, null)
            val totalSMS: Int = cursor!!.count

            if (cursor.moveToFirst()) {
                Log.d("TAG", "getAllSMS: count : $totalSMS ")
                for (i in 0 until totalSMS) {
                    Log.d("TAG", "getAllSMS: index : $i ")
                    val smsSource = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                    val smsBody = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                    val regEx =
                        Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}") // two digit - six digit
                    val matcher = regEx.matcher(smsSource)
                    val isSMSFromBank = BANK_TYPES.any { it in smsSource.uppercase() }
                    if (matcher.find() && isSMSFromBank) { // TO TEST ON DEVICE
//                    if(smsSource.equals("1234567890")){ // TO TEST ON EMULATOR
                        Log.d("TAG", "getAllSMS: hello number $smsSource")
                        if (smsBody.contains("debited") || smsBody.contains("credited") || smsBody.contains(
                                "received"
                            ) || smsBody.contains("purchase") || smsBody.contains("withdrawn")
                        ) {
                            val objectSMS = SMS(
                                id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                                source = smsSource,
                                msgBody = smsBody,
                                time = cursor.getLong(cursor.getColumnIndexOrThrow("date"))
                            )

                            lstSms.add(objectSMS)
                        }
                    } else {
                        Log.d("TAG", "getAllSMS: not found anything $smsSource $smsBody ")
                        FirebaseCrashlytics.getInstance().apply {
                            log("No Crash : getAllSMS: not found anything $smsSource $smsBody")
                        }
                    }
                    cursor.moveToNext()
                    Log.d("TAG", "getAllSMS: hello $lstSms")
                }
            }
            cursor.close()

            return Resource.Success(
                data = lstSms
            )

        } catch (exception: Exception) {
            FirebaseCrashlytics.getInstance().apply {
                log("No Crash : Exception ${exception.message}")
            }
            return Resource.Error(
                exception.message ?: "unknown Exception"
            )
        }

    }

    override suspend fun getAllTransactions(messageList: List<SMS>?): Resource<List<Transaction>> {
        val transactionList: MutableList<Transaction> = ArrayList()
        try {
            for (sms in messageList!!) {
                val body = sms.msgBody
                val transaction = Transaction(
                    id = 1,
                    amount = getAmount(body),
                    isCredited = isCredited(body),
                    sourceName = sms.source,
                    date = sms.time,
                    bankName = sms.source.substring(2, 8),
                    msgBody = sms.msgBody
                )
                if (!transaction.msgBody.lowercase().contains("otp"))
                    transactionList.add(transaction)
            }
            return Resource.Success(data = transactionList)
        } catch (ex: Exception) {
            FirebaseCrashlytics.getInstance().apply {
                log("No Crash : Exception ${ex.message}")
            }
            return Resource.Error(ex.message ?: "unknown Exception")
        }


    }

    override suspend fun getAllTransactionsOfBank(
        messageList: List<SMS>?,
        bankType: String
    ): Resource<List<Transaction>> {
        println("getAllTransactionsOfBank $bankType")
        val transactionList: MutableList<Transaction> = ArrayList()
        try {
            for (sms in messageList!!) {
                val body = sms.msgBody
                val transaction = Transaction(
                    id = 1,
                    amount = getAmount(body),
                    isCredited = isCredited(body),
                    sourceName = sms.source,
                    date = sms.time,
                    bankName = sms.source.substring(2, 8),
                    msgBody = sms.msgBody
                )
                var regex = ""
                if (bankType in BANK_TYPES) regex = bankType
                println("getAllTransactionsOfBank reg $regex")
                if (transaction.sourceName.uppercase()
                        .contains(regex) || transaction.msgBody.uppercase()
                        .contains(regex) && transaction.msgBody.lowercase().contains("otp").not()
                )
                    transactionList.add(transaction)
                else{
                    println("getAllTransactionsOfBank error ${transaction.sourceName.uppercase()}")
                }

                println("getAllTransactionsOfBank list $transactionList")
            }
            return Resource.Success(data = transactionList)
        } catch (ex: Exception) {
            FirebaseCrashlytics.getInstance().apply {
                log("No Crash : Exception ${ex.message}")
            }
            return Resource.Error(ex.message ?: "unknown Exception")
        }


    }


    private fun getBankName(messageBody: String): String {
        val regEx = Pattern.compile(MERCHANT)
        val matcher = regEx.matcher(messageBody)
        if (matcher.find()) {
            try {
                Log.e("bank name ", " ${matcher.group(0)}")
                return "" + matcher.group(0)

            } catch (e: Exception) {
                return ""
            }
        } else {
            return ""
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
        val regEx = Pattern.compile(AMOUNT)
        val matcher = regEx.matcher(messageBody)
        if (matcher.find()) {
            try {
                Log.e("amount_value= ", " ${matcher.group(0)}")
                return "" + matcher.group(0)

            } catch (e: Exception) {
                return ""
            }
        } else {
            return ""
        }
    }
}