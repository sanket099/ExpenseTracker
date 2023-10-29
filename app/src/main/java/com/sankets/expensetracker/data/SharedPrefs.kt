package com.sankets.expensetracker.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefs @Inject constructor(application: Application) {

    companion object {
        private const val SHARED_PREF_NAME = "TransactionSharedPref"
    }

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

//    fun putBalance(key: String, value: Double){
//        editor.putFloat(key, value.toFloat()).apply()
//    }
//    fun getBalance(key: String): Double {
//        return sharedPreferences.getFloat(key, 0F).toDouble()
//    }

    fun putBanks(key: String, value: Set<String>){
        editor.putStringSet(key, value).apply()
    }
    fun getBanks(key: String): MutableSet<String>? {
        return sharedPreferences.getStringSet(key, emptySet())
    }
    fun setBoolean(key: String, value : Boolean){
        editor.putBoolean(key, value).apply()
    }
    fun getBoolean(key: String, defaultValue : Boolean) : Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

}