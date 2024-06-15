package com.finance.android.walletwise.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryRepository
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.model.Category.isValid
import com.finance.android.walletwise.model.Category.toCategory
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.model.Transaction.isValid
import com.finance.android.walletwise.model.Transaction.toTransaction
import kotlinx.coroutines.flow.MutableStateFlow


class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel() {

//    private val _selectedCategory = MutableStateFlow<Category?>(null)
//    val selectedCategory = _selectedCategory
//    private val _allCategories = MutableStateFlow<List<Category>>(emptyList())
//    val allCategories = _allCategories

    var categoryUiState by mutableStateOf(CategoryUIState())

        private set

    fun updateUiState(newCategoryUIState: CategoryUIState){
        categoryUiState= newCategoryUIState.copy(actionEnabled = newCategoryUIState.isValid())
    }

    fun getCategoryById(id: Int) {
        if(categoryUiState.isValid()){
            categoryUiState=categoryUiState.copy()
            categoryRepository.getCategoryStream(id)
        }
    }
    fun getAllCategories() {
        if(categoryUiState.isValid()){
            categoryUiState=categoryUiState.copy()
            categoryRepository.getCategoriesStream()
        }
    }

    suspend fun saveCategory(){
        if(categoryUiState.isValid()){
            categoryUiState=categoryUiState.copy()
            categoryRepository.insertCategory(categoryUiState.toCategory())
        }
    }
    fun updateNameCategory(category: String) {
        categoryUiState = categoryUiState.copy(name = category)
    }
    fun updateAmount(amount: Int) {
        categoryUiState = categoryUiState.copy(amount = amount.toString())
    }




}