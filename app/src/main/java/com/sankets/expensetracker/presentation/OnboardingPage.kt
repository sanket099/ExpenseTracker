package com.sankets.expensetracker.presentation

import androidx.annotation.DrawableRes
import com.sankets.expensetracker.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding_asset_1,
        title = "Expense Tracking, Your Way",
        description = "Track your expenses offline, without linking bank apps or any manual entry.\nWith Expense Log, you're in control.\nYour financial data stays secure and private.\n Take the first step towards understanding your spending habits."
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding_asset_2,
        title = "SMS Integration Simplified",
        description = "We've made expense tracking a breeze.\nExpense Log integrates with your SMS notifications, automatically organizing your expenses for you."
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboarding_asset_3,
        title = "Plan, Save, and Thrive",
        description = "Expense Log empowers you with clear insights into your spending habits.\nYour journey towards financial well-being starts now"
    )
}