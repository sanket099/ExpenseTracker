package com.sankets.expensetracker

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.Navigation
import com.sankets.expensetracker.presentation.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
                viewModel.isSplashScreenShown.value
            })
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            val banks = viewModel.sharedPrefs.getBanks(BANKS)?.toMutableList()

            if (banks?.isEmpty()?.not()!!)
                viewModel.loadTransactionInfo("ALL")
//                viewModel.loadTransactionInfo(bankState = banks[0]) // to try to get transaction
            else {
                viewModel.loadTransactionInfo() // to try to get transaction
            }
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            )
        )

        setContent {
//            ExpenseTrackerTheme {
//                ExpenseHome(viewModel = viewModel)
//            }
            Navigation(viewModel)
        }
    }
}

