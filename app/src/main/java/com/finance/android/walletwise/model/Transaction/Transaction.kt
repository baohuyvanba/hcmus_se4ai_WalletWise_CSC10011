package com.finance.android.walletwise.model.Transaction

import androidx.room.*
import java.time.LocalDate
import java.time.LocalTime


@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "type") val type: String, // "income" or "expense"
    @ColumnInfo(name = "date") val date: LocalDate, // Unix timestamp in milliseconds
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "description") val description: String

)