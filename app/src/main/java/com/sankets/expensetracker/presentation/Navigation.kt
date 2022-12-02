package com.sankets.expensetracker.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sankets.expensetracker.data.SharedPrefs
import com.sankets.expensetracker.domain.util.Constants.BANKS

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
        composable(route = Screen.AvlBalScreen.route) {
            GetBanks(viewModel = viewModel, navController = navController)
        }
    }
}

fun whichScreen(sharedPrefs: SharedPrefs): String{
    return if(sharedPrefs.getBanks(BANKS) == emptySet<String>()){
        Screen.AvlBalScreen.route
    }
    else{
        Screen.HomeScreen.route
    }
}