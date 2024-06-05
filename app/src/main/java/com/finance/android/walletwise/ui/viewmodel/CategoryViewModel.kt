package com.finance.android.walletwise.viewmodel

import androidx.lifecycle.*
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    // Observing all categories
    val allCategories: LiveData<List<Category>> = repository.allCategories.asLiveData()

    // Fetch a specific category by ID
    fun getCategoryById(id: Int): LiveData<Category> {
        return repository.getCategoryById(id).asLiveData()
    }

    // Insert a new category
    fun insertCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    // Update an existing category
    fun updateCategory(category: Category) = viewModelScope.launch {
        repository.updateCategory(category)
    }

    // Delete a category
    fun deleteCategory(category: Category) = viewModelScope.launch {
        repository.deleteCategory(category)
    }
}

class CategoryViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
