package com.sankets.expensetracker.presentation

// only allows classes inside this class to inherit from this class
sealed class Screen(val route : String) {
    object HomeScreen: Screen("home")
    object SmsScreen: Screen("sms_screen")
    object AvlBalScreen : Screen("available_balance")

    fun withArgs(vararg args : String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}