package com.sankets.expensetracker

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.Navigation
import com.sankets.expensetracker.presentation.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @Inject
    lateinit var appDelegate: AppDelegate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
                viewModel.isSplashScreenShown.value
            })
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { mapOfPermissions ->
            viewModel.sharedPrefs.setBoolean(Constants.ARE_PERMISSIONS_GRANTED, true)
            Timber.tag(Constants.LOG_MAIN).d("Permissions : $mapOfPermissions")
            if(mapOfPermissions[Manifest.permission.READ_SMS] == true && mapOfPermissions[Manifest.permission.RECEIVE_SMS] == true){
                viewModel.sharedPrefs.setBoolean(Constants.ARE_PERMISSIONS_GRANTED, true)
                appDelegate.initApp()

                viewModel.loadTransactionInfo(Constants.ALL) // to try to get transaction
            }
            else{
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS
                    )
                )
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

