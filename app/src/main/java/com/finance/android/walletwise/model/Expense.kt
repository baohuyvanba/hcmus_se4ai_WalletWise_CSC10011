package com.finance.android.walletwise.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Entity(tableName = "category")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val iconName: String,
    val budget: Double
)

val categories = listOf(
    ExpenseCategory(1, "Shopping", "ShoppingCart", 100.0),
    ExpenseCategory(2, "House", "Home", 100.0),
)

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    @ColumnInfo(name = "category_name") val categoryName: String,
    val description: String,
    val date: String,
    val type: String
) {
    val category: ExpenseCategory?
        get() = categories.find { it.name == categoryName }
}

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: ExpenseCategory): Long

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<ExpenseCategory>
}

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Expense): Long
}