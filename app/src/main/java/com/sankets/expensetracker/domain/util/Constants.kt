package com.sankets.expensetracker.domain.util

import javax.annotation.concurrent.Immutable

object Constants {

    const val ALL = "ALL"
    const val TRANSACTION_TABLE = "transaction_table"

    const val AMOUNT = "(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)"

//    const val MERCHANT = "(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)"

    const val MERCHANT =
        "(?i)(?:\\\\sInfo.\\\\s*)([A-Za-z0-9*]*\\\\s?-?\\\\s?[A-Za-z0-9*]*\\\\s?-?\\\\.?)"

    val FILTER_BANK = listOf<String>(
        "[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}",
        "credited",
        "debited",

        )
    const val AVAILABLE_BALANCE = "AVAILABLE_BALANCE"

    const val BANKS = "BANKS"

    val BANK_TYPES = listOf<String>(
        "ALL",
        "HDFC",
        "SBI",
        "PAYTM",
        "AXIS",
        "KOTAK",
        "ICICI",
    )

    // Worker
    val workerRequestTime = 1L
    val TAG_WORKER = "PERIODIC_TAG_SMS_WORKER"
    val SMS_WORK_ID = "PERIODIC_SMS_WORK_ID"
    val TAG_ONETIME_WORKER= "ONETIME_TAG_SMS_WORKER"
    val SMS_ONETIME_WORK_ID = "ONETIME_SMS_WORK_ID"

    // Broadcast Received
    val NUMBER_OF_NEW_MESSAGES_RECIEVED: String = "NUMBER_OF_NEW_MESSAGES_RECIEVED"

    // SharedPreferences
    const val IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH"
    const val ARE_PERMISSIONS_GRANTED = "ARE_PERMISSIONS_GRANTED"

    // Timber TAGS
    const val LOG_WORKER = "LOG_WORKER"
    const val LOG_MAIN = "MAIN_ACTIVITY"
    const val LOG_VIEWMODEL = "LOG_VIEWMODEL"
}