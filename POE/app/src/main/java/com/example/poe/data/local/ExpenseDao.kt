package com.example.poe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    // -----------------------------
    // EXPENSES
    // -----------------------------

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    // -----------------------------
    // CATEGORIES
    // -----------------------------

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>
}