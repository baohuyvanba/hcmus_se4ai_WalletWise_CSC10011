package com.finance.android.walletwise.model.Category

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Flow<Category>

//    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
//    fun getCategoryByName(name: String): Flow<Category?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(Category: Category)

    @Update
    suspend fun updateCategory(Category: Category)

    @Delete
    suspend fun deleteCategory(Category: Category)
}