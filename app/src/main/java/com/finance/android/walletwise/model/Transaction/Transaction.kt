package com.finance.android.walletwise.model.Transaction

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.finance.android.walletwise.model.Category.Category
import java.time.LocalDate
import java.time.LocalTime

//@Entity(
//    tableName = "transaction_table",
//    foreignKeys = [
//        ForeignKey(
//            entity = Category::class,
//            parentColumns = arrayOf("name"),
//            childColumns = arrayOf("category_name"),
//            onDelete = CASCADE,
//        )
////        ,
////        ForeignKey(
////            entity = Account::class,
////            parentColumns = arrayOf("id"),
////            childColumns = arrayOf("account_id"),
////            onDelete = CASCADE
////        )
//    ]
//)
@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category_name") val category: String,
    @ColumnInfo(name = "type") val type: String, // "income" or "expense"
    @ColumnInfo(name = "date") val date: LocalDate, // Unix timestamp in milliseconds
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "description") val description: String

)
//data class ExpenseWithRelation(
//    @Embedded
//    val expense: Transaction,
//    @Relation(
//        parentColumn = "category_name",
//        entityColumn = "name"
//    )
//    val category: Category,
////    @Relation(
////        parentColumn = "account_id",
////        entityColumn = "id"
////    )
////    val account: Account
//)