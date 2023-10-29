package com.sankets.expensetracker.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.domain.util.Constants.TRANSACTION_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM $TRANSACTION_TABLE")
    fun getTransactions(): Flow<List<Transaction>>
    @Query("SELECT * FROM $TRANSACTION_TABLE where bank_type = :bankType")
    fun getTransactionBasedOnBankType(bankType : String): Flow<List<Transaction>>
}