package com.example.poe.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * Room Database class for the BudgetBuddy app
 * Handles all database entities and provides DAO access
 */
@Database(
    entities = [
        UserEntity::class,      // User accounts
        ExpenseEntity::class,    // Expense records
        CategoryEntity::class,   // Expense categories
        BudgetGoalEntity::class  // Monthly budget goals
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Abstract method to access DAO
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //Singleton pattern to ensure only one database instance exists

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}