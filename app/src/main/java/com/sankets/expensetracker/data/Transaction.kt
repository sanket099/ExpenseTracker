package com.sankets.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sankets.expensetracker.domain.util.Constants.TRANSACTION_TABLE

/**
 *
 */
@Entity(tableName = TRANSACTION_TABLE)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "isCredited")
    val isCredited: Boolean,
    @ColumnInfo(name = "bank_name")
    val bankName : String,
    @ColumnInfo(name = "source_name")
    val sourceName : String,
    @ColumnInfo(name = "date")
    val date : Long,
    @ColumnInfo(name = "msg_body")
    val msgBody : String
)