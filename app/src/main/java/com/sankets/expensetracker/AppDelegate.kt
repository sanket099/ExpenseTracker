package com.sankets.expensetracker

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.workers.TransactionWorker
import java.util.concurrent.TimeUnit
import androidx.work.Data
import javax.inject.Inject

class AppDelegate @Inject constructor(
    private val application: Application,
    private val sharedPrefs: SharedPrefs
) {

    fun initApp(){
        if(sharedPrefs.getBoolean(Constants.ARE_PERMISSIONS_GRANTED, false)){
            startWorkerToSaveSMS(application)
        }
    }

    private fun startWorkerToSaveSMS(application: Application){
        val smsWorkRequest = PeriodicWorkRequestBuilder<TransactionWorker>(
            Constants.workerRequestTime, TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).addTag(Constants.TAG_WORKER).build()
        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            Constants.SMS_WORK_ID, ExistingPeriodicWorkPolicy.KEEP, smsWorkRequest
        )
    }

    fun startOneTimeWorkerToSaveSMS(context: Context, data: Data){
        val oneTimeRequest = OneTimeWorkRequestBuilder<TransactionWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setInputData(data)
            .addTag(Constants.TAG_ONETIME_WORKER)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            Constants.SMS_ONETIME_WORK_ID,
            ExistingWorkPolicy.KEEP,
            oneTimeRequest
        )
    }



}