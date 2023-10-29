package com.sankets.expensetracker.data.di

import android.app.Application
import com.sankets.expensetracker.data.storage.StorageManager
import com.sankets.expensetracker.domain.repository.TransactionRepo
import com.sankets.expensetracker.domain.repository.TransactionRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindTransactionRepo(application: Application, storageManager: StorageManager) : TransactionRepo = TransactionRepoImpl(application = application, storageManager)

}