package com.sankets.expensetracker.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

// TODO

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in smsMessages) {
                // Do whatever you want to do with SMS.
                Log.d("TAG", "onReceive: ${message.messageBody}")
            }
        }
    }
}