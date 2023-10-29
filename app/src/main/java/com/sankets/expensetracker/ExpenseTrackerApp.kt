package com.sankets.expensetracker

import android.app.Application
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ExpenseTrackerApp : Application(), Configuration.Provider {
    @Inject
    lateinit var appDelegate: AppDelegate

    @Inject
    lateinit var hiltFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appDelegate.initApp()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
//            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.ERROR)
            .setWorkerFactory(hiltFactory).build()
    }
}