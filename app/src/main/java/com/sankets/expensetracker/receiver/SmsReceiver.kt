package com.sankets.expensetracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import androidx.work.Data
import com.sankets.expensetracker.AppDelegate
import com.sankets.expensetracker.domain.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appDelegate: AppDelegate

    override fun onReceive(context: Context?, intent: Intent) {
        Timber.tag("Broadcast Receiver").d("intent ${intent.action}")
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val numberOfMessagesReceived = smsMessages.size
            val data = Data.Builder()
            data.putInt(Constants.NUMBER_OF_NEW_MESSAGES_RECIEVED,numberOfMessagesReceived)
            val inputData = data.build()
            if (context != null) {
                appDelegate.startOneTimeWorkerToSaveSMS(context, inputData)
            }
        }
    }
}