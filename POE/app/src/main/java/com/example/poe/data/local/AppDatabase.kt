package com.example.poe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
}