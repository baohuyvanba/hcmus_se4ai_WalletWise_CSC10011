//package com.finance.android.walletwise.model.Category
//
//import CategoryRepository
//import OfflineCategoryRepository
//import android.content.Context
//import com.finance.android.walletwise.model.Transaction.OfflineTransactionRepository
//import com.finance.android.walletwise.model.Transaction.TransactionDatabase
//import com.finance.android.walletwise.model.Transaction.TransactionRepository
//
//interface AppContainer {
//    val transactionRepository: TransactionRepository
//}
//
//class AppContainer(context: Context) {
//    interface AppContainer {
//        val transactionRepository: TransactionRepository
//    }
//    private val categoryDao: CategoryDao = CategoryDatabase.getDatabase(context).categoryDao()
//
//    val categorysRepository: CategoryRepository by lazy {
//        OfflineCategoryRepository(categoryDao)
//    }
//}
