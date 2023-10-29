package com.sankets.expensetracker.data.di

import android.app.Application
import com.sankets.expensetracker.AppDelegate
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.data.di.coroutine.CoroutineDispatcherProvider
import com.sankets.expensetracker.data.di.coroutine.CoroutineDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()

    @Provides
    @Singleton
    fun provideAppDelegate(
        application: Application,
        sharedPrefs: SharedPrefs
    ): AppDelegate = AppDelegate(application, sharedPrefs)

}