package com.sankets.expensetracker.data

import java.io.Serializable

data class Banks (
    val id: Int,
    val name: String,
    val icon: String,
    var isSelected: Boolean
) : Serializable {
}