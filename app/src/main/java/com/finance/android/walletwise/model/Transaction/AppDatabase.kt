package com.finance.android.walletwise.model.Transaction

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryDao
import java.util.concurrent.Executors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//import androidx.room.TypeConverters

//val defaultExpenseCategoryDtoTypeList = listOf(
//    Category(id = 0, name = "INCOME", amount = 0),
//    Category(id = 1, name = "Food", amount = 0)
//)
@Database(entities = [Transaction::class, Category::class], version = 6, exportSchema = false)
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

//val defaultExpenseCategoryDtoTypeList = listOf(
//    Category(id = 0, name = "INCOME", amount = 0),
//    Category(id = 1, name = "Food", amount = 0)
//)
//
//@Database(entities = [Transaction::class, Category::class], version = 8, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun transactionDao(): TransactionDao
//    abstract fun categoryDao(): CategoryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                Room.databaseBuilder(context.applicationContext,
//                    AppDatabase::class.java, "app_database"
//                )
//                    .addCallback(databaseCallback)
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
//            }
//        }
//        fun getInstance(): AppDatabase? {
//            return INSTANCE
//        }
//    }
//}
//
//val databaseCallback = object : RoomDatabase.Callback() {
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//        Log.d("AppDatabase", "onCreate: Database has been created.")
//        GlobalScope.launch {
//            AppDatabase.getInstance()?.let { database ->
//                val categoryDao = database.categoryDao()
//                for (category in defaultExpenseCategoryDtoTypeList) {
//                    categoryDao.insertCategory(category)
//                }
//            }
//        }
//    }
//}
//
//private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
//
//fun ioThread(f: () -> Unit) {
//    IO_EXECUTOR.execute(f)
//}