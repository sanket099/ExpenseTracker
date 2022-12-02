package com.sankets.expensetracker.data.di

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

}