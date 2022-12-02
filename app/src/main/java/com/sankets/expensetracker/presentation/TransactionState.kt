package com.sankets.expensetracker.presentation

import com.sankets.expensetracker.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TransactionState(
//    val availableBalance : Double = 0.0,
    val listTransactions : List<Transaction>? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)