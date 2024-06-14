package com.finance.android.walletwise.model.Transaction

import android.content.Context


interface AppContainer {
    val transactionRepository: TransactionRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val transactionRepository: TransactionRepository by lazy{
        OfflineTransactionRepository(TransactionDatabase.getDatabase(context).transactionDao())
    }
}