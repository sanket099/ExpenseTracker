package com.sankets.expensetracker.data.di

import android.app.Application
import androidx.room.Room
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.data.di.coroutine.CoroutineDispatcherProvider
import com.sankets.expensetracker.data.storage.StorageManager
import com.sankets.expensetracker.data.storage.TransactionDao
import com.sankets.expensetracker.data.storage.TransactionDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private const val DATABASE_NAME = "transaction.db"

    @Provides
    @Singleton
    fun provideTransactionDb(application: Application): TransactionDb {
        return Room.databaseBuilder(application, TransactionDb::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(transactionDatabase: TransactionDb): TransactionDao =
        transactionDatabase.getTransactionDao()

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPrefs = SharedPrefs(application)

    @Provides
    @Singleton
    fun providesStorageManager(dispatcherProvider: CoroutineDispatcherProvider, transactionDao: TransactionDao, sharedPrefs: SharedPrefs) : StorageManager = StorageManager(dispatcherProvider, transactionDao, sharedPrefs)



}