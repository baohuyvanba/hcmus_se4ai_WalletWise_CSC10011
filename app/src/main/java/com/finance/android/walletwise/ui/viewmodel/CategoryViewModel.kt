package com.finance.android.walletwise.ui.viewmodel

import CategoryRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.model.Category.isValid


class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel() {

    var categoryUiState by mutableStateOf(CategoryUIState())

        private set

    fun updateUiState(newCategoryUIState: CategoryUIState){
        categoryUiState= newCategoryUIState.copy(actionEnabled = newCategoryUIState.isValid())
    }
}