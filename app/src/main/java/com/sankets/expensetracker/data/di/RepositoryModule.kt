package com.sankets.expensetracker.data.di

import android.app.Application
import com.sankets.expensetracker.domain.repository.TransactionRepo
import com.sankets.expensetracker.domain.repository.TransactionRepoImpl
import dagger.Binds
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
    fun bindWeatherRepo(application: Application) : TransactionRepo = TransactionRepoImpl(application = application)

}