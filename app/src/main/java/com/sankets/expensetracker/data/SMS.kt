package com.sankets.expensetracker.data

data class SMS(
    val id : String,
    val source : String,
    val msgBody : String,
    val time: Long
)