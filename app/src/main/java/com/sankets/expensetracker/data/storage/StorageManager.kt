package com.sankets.expensetracker.data.storage

import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.data.di.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StorageManager @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val transactionDao: TransactionDao,
    private val sharedPrefs: SharedPrefs,
) {
    suspend fun insertTransactionIntoDB(transaction: Transaction) = withContext(dispatcher.ioDispatcher){
        transactionDao.insertTransaction(transaction = transaction)
    }

    suspend fun fetchTransactionFromDB() : Flow<List<Transaction>> = withContext(dispatcher.ioDispatcher){
        return@withContext transactionDao.getTransactions()
    }
//    suspend fun fetchTransactionFromDBBasedOnBank(bankType : String) : Flow<List<Transaction>> = withContext(dispatcher.ioDispatcher){
//        return@withContext transactionDao.getTransactionBasedOnBankType(bankType)
//    }
}