package com.sankets.expensetracker.domain.repository

import android.app.Application
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.data.storage.StorageManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TransactionRepoImpl @Inject constructor(
    private val application: Application,
    private val storageManager: StorageManager
) : TransactionRepo {


    override suspend fun fetchTransactionsFromDB(): Flow<List<Transaction>> {
        return storageManager.fetchTransactionFromDB()
    }
}