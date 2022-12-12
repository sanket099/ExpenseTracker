package com.sankets.expensetracker.presentation

// only allows classes inside this class to inherit from this class
sealed class Screen(val route : String) {
    object HomeScreen: Screen("home")
    object SmsScreen: Screen("sms_screen")
    object ChooseBanksScreen : Screen("choose_banks")
    object DetailTransactionScreen : Screen("detail_transactions")

    fun withArgs(vararg args : String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}