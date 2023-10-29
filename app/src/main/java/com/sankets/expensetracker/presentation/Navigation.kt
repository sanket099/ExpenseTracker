package com.sankets.expensetracker.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.domain.util.Constants.IS_FIRST_LAUNCH

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(viewModel: TransactionViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = whichScreen(viewModel.sharedPrefs)) {
        composable(route = Screen.HomeScreen.route,
//        arguments = listOf(
//            navArgument("balance"){
//                type = NavType.StringType
//                defaultValue = ""
//                nullable = false
//            },
//
//        )
        ) {
            ExpenseHome(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.SmsScreen.route + "/{sms_source}/{sms_body}/{sms_date}",
            arguments = listOf(
                navArgument("sms_source") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("sms_body") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("sms_date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )
        ) { navBackStackEntry ->
            SmsDetailScreen(
                smsSource = navBackStackEntry.arguments?.getString("sms_source"),
                smsBody = navBackStackEntry.arguments?.getString("sms_body"),
                smsDate = navBackStackEntry.arguments?.getString("sms_date")
            )
        }
        composable(route = Screen.ChooseBanksScreen.route) {
            GetBanks(viewModel = viewModel, navController = navController)
        }
//        composable(
//            route = Screen.DetailTransactionScreen.route
//        ){
//            DetailTransactions(viewModel = viewModel, navController = navController)
//        }
        composable(
            route = Screen.OnboardingScreen.route
        ){
            OnboardingScreen(viewModel, navController = navController)
        }
        composable(
            route = Screen.PrivacyPolicyScreen.route
        ){
            PrivacyPolicyScreen()
        }
    }
}

fun whichScreen(sharedPrefs: SharedPrefs): String{
    return if(sharedPrefs.getBoolean(IS_FIRST_LAUNCH, true)){
        Screen.OnboardingScreen.route
    }
    else if(sharedPrefs.getBanks(BANKS) == emptySet<String>()){
        Screen.ChooseBanksScreen.route
    }
    else{
        Screen.HomeScreen.route
    }
}