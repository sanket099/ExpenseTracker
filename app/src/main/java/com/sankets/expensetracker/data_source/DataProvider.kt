package com.sankets.expensetracker.data_source

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log

object DataProvider {

    fun getSMS(context : Context) {

        val cursor: Cursor? = context.contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)

        if (cursor!!.moveToFirst()) { // must check the result to prevent exception
            do {
                var msgData = ""
                for (idx in 0 until cursor.columnCount) {
                    msgData += " " + cursor.getColumnName(idx).toString() + ":" + cursor.getString(
                        idx
                    )
                    Log.d("TAG", "getSMS: $msgData")
                }

                // use msgData
            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
        }
    }



}