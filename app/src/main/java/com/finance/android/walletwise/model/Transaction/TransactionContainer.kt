package com.finance.android.walletwise.model.Transaction

import CategoryRepository
import OfflineCategoryRepository
import android.content.Context


interface AppContainer {
    val transactionRepository: TransactionRepository
    val categoryRepository: CategoryRepository

}

class AppDataContainer(private val context: Context): AppContainer{
    override val transactionRepository: TransactionRepository by lazy{
        OfflineTransactionRepository(AppDatabase.getDatabase(context).transactionDao())
    }

    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(AppDatabase.getDatabase(context).categoryDao())
    }
}