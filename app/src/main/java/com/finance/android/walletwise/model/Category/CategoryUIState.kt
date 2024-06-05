package com.finance.android.walletwise.model.Category

import com.finance.android.walletwise.model.Transaction.TransactionUiState

data class CategoryUIState(
    val id: Int = 0,
    val name: String = "",
    val icon: String? = null,
    val amount: Int =0,
    val actionEnabled: Boolean = false
)

fun CategoryUIState.toCategory(): Category {
    return Category(id, name, icon, amount)
}

fun Category.toCategoryUIState(actionEnabled: Boolean=false): CategoryUIState {
    return CategoryUIState(id, name, icon, amount,actionEnabled)
}

fun CategoryUIState.isValid(): Boolean {
    return name.toString().isNotBlank()
}