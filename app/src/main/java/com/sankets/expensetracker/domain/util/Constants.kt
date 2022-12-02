package com.sankets.expensetracker.domain.util

import javax.annotation.concurrent.Immutable

object Constants {

    const val TRANSACTION_TABLE = "transaction_table"

    const val AMOUNT = "(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)"

//    const val MERCHANT = "(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)"

    const val MERCHANT = "(?i)(?:\\\\sInfo.\\\\s*)([A-Za-z0-9*]*\\\\s?-?\\\\s?[A-Za-z0-9*]*\\\\s?-?\\\\.?)"

    val FILTER_BANK = listOf<String>(
        "[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}",
        "credited",
        "debited",

    )
    const val AVAILABLE_BALANCE = "AVAILABLE_BALANCE"

    const val BANKS = "BANKS"

}