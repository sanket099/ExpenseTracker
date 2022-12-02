package com.sankets.expensetracker

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.ExpenseCard
import com.sankets.expensetracker.presentation.ExpenseHome
import com.sankets.expensetracker.presentation.Navigation
import com.sankets.expensetracker.presentation.TransactionViewModel
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.ExpenseTrackerTheme
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


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
            val banks = viewModel.sharedPrefs.getBanks(BANKS)?.toList()
            if(banks?.isEmpty()?.not()!!)
                viewModel.loadTransactionInfo(bankState = banks[0]) // to try to get transaction
            else{
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

