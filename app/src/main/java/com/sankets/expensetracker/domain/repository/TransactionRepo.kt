package com.sankets.expensetracker.domain.repository

import android.content.Context
import com.sankets.expensetracker.data.SMS
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.domain.util.Resource
import kotlinx.coroutines.flow.StateFlow

interface TransactionRepo {
    suspend fun getAllSMS(context : Context) : Resource<List<SMS>>?
    suspend fun getAllTransactions(messageList : List<SMS>?) : Resource<List<Transaction>>
    suspend fun getAllTransactionsOfBank(messageList: List<SMS>?, bankType : String) : Resource<List<Transaction>>

}