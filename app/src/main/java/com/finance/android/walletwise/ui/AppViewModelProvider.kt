package com.finance.android.walletwise.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finance.android.walletwise.ui.viewmodel.ExpenseViewModel
import com.finance.android.walletwise.WalletWiseApplicatiom
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.EditTransactionViewModel
import com.finance.android.walletwise.ui.viewmodel.TransactionsScreenViewModel
import com.finance.android.walletwise.model.Category.CategoryRepository


object AppViewModelProvider{
    val Factory= viewModelFactory {
        initializer {
            CategoryViewModel(
                transacrionApplicatiom().container.categoryRepository
            )
        }
        initializer {
            ExpenseViewModel(
                transacrionApplicatiom().container.transactionRepository
            )
        }
        initializer {
            TransactionsScreenViewModel(
                transacrionApplicatiom().container.transactionRepository
            )
        }

        initializer {
            EditTransactionViewModel(
                this.createSavedStateHandle(),
                transacrionApplicatiom().container.transactionRepository
            )
        }

    }
}

fun CreationExtras.transacrionApplicatiom():WalletWiseApplicatiom=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WalletWiseApplicatiom)