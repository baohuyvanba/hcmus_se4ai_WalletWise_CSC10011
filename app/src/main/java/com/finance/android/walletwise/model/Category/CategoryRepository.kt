package com.finance.android.walletwise.model.Category

import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryDao
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Fetch all categories
    val allCategories: Flow<List<Category>> = categoryDao.getCategories()

    // Fetch a single category by its ID
    fun getCategoryById(id: Int): Flow<Category> {
        return categoryDao.getCategoryById(id)
    }

    // Insert a new category into the database
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    // Update an existing category
    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    // Delete a category from the database
    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}
