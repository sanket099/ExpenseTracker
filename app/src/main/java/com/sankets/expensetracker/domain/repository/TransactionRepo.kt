package com.sankets.expensetracker.domain.repository

import com.sankets.expensetracker.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {
    suspend fun fetchTransactionsFromDB() : Flow<List<Transaction>>
}