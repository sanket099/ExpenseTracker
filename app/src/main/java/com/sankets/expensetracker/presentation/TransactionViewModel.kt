package com.sankets.expensetracker.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.repository.TransactionRepo
import com.sankets.expensetracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepo,
    private val application: Application

): ViewModel() {

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

    fun loadTransactionInfo(bankState : String = "") {
        viewModelScope.launch {
            state = state.copy( // to change state // to be updated in compose
                isLoading = true,
                error = null
            )
            repository.getAllSMS(application)?.let { SMSList -> // if we get the location
                when (val result =
                    repository.getAllTransactionsOfBank(SMSList.data, bankState)) {
                    is Resource.Success -> {
                        Log.d("TAG", "loadTransactionInfo: ${result.data}")
//                        updateBalance(result.data)
                        state = state.copy( // to update ui
                            listTransactions = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> { // to update ui
                        Log.e("TAG", "loadTransactionInfo: ${result.data}")
                        state = state.copy(
                            listTransactions = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

            } ?: kotlin.run { // if location is null
                state = state.copy( // to update ui
                    isLoading = false,
                    error = "Couldn't retrieve sms. Make sure to grant permission."
                )
            }
        }
    }


//    private fun updateBalance(data: List<Transaction>?) {
//
//        var avlBal = sharedPrefs.getBalance(AVAILABLE_BALANCE).toDouble()
//        Log.d("TAG", "updateBalance: $avlBal")// get from shared pref
//        for (transaction in data!!){
//            if(transaction.isCredited){
//                avlBal += getAmount(transaction.amount)
//            }
//            else{
//                avlBal -= getAmount(transaction.amount)
//            }
//        }
//        sharedPrefs.putBalance(AVAILABLE_BALANCE, avlBal)
//
//    }

//    private fun getAmount(paisa : String) : Double{
//        val result = paisa.filter { it.isDigit() || it == '.'}
//        Log.d("TAG", "getAmount: $result")
//        return result.toDouble()
//    }

}

