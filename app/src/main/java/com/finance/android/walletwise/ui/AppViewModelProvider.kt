package com.finance.android.walletwise.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finance.android.walletwise.ui.viewmodel.ExpenseViewModel
import com.finance.android.walletwise.WalletWiseApplicatiom
import com.finance.android.walletwise.ui.viewmodel.TransactionsScreenViewModel

object AppViewModelProvider{
    val Factory= viewModelFactory {
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

    }
}

fun CreationExtras.transacrionApplicatiom():WalletWiseApplicatiom=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WalletWiseApplicatiom)