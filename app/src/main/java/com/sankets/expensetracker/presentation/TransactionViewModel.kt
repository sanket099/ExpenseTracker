package com.sankets.expensetracker.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.repository.TransactionRepo
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepo,
    private val application: Application

) : ViewModel() {

    var state by mutableStateOf(TransactionState())
        private set // only vm can change state

    private val showSplashScreen = MutableStateFlow(true)
    val isSplashScreenShown = showSplashScreen.asStateFlow()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    init {
        viewModelScope.launch {
            delay(3000)
            showSplashScreen.value = false
        }
    }

    fun loadTransactionInfo(bankState: String) {
        viewModelScope.launch {
            state = state.copy( // to change state // to be updated in compose
                isLoading = true,
                error = null
            )
            val result = repository.fetchTransactionsFromDB()
            result.collectLatest { transactions ->
                val filteredTransactions = if (bankState == Constants.ALL) {
                    transactions
                } else {
                    transactions.filter { it.bankType == bankState }
                }

                Timber.tag(Constants.LOG_VIEWMODEL)
                    .d("Fetch All" + if (bankState != Constants.ALL) " with $bankState" else "" + " : $filteredTransactions")

                state = state.copy(
                    listTransactions = filteredTransactions,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

}

