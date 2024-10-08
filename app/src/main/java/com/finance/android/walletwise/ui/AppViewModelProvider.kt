package com.finance.android.walletwise.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finance.android.walletwise.ui.viewmodel.transaction.ExpenseViewModel
import com.finance.android.walletwise.WalletWiseApplication
import com.finance.android.walletwise.ui.viewmodel.category.CategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.transaction.EditTransactionViewModel
import com.finance.android.walletwise.ui.viewmodel.category.EditCategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.transaction.TransactionsScreenViewModel


object AppViewModelProvider{
    val Factory= viewModelFactory {
        initializer {
            CategoryViewModel(
                transactionApplication().container.categoryRepository
            )
        }
        initializer {
            ExpenseViewModel(
                transactionApplication().container.transactionRepository
            )
        }
        initializer {
            TransactionsScreenViewModel(
                transactionApplication().container.transactionRepository
            )
        }

        initializer {
            EditTransactionViewModel(
                this.createSavedStateHandle(),
                transactionApplication().container.transactionRepository
            )
        }
        initializer {
            EditCategoryViewModel(
                this.createSavedStateHandle(),
                transactionApplication().container.categoryRepository
            )
        }

    }
}

fun CreationExtras.transactionApplication():WalletWiseApplication=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WalletWiseApplication)