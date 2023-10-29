package com.sankets.expensetracker.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sankets.expensetracker.data.Transaction

@Database(version = 1, entities = [Transaction::class], exportSchema = false)
abstract class TransactionDb : RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao
}