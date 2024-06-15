package com.finance.android.walletwise.model.Transaction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryDao

//import androidx.room.TypeConverters

@Database(entities = [Transaction::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                    .also {
                    instance = it
                }
            }
        }
    }
}