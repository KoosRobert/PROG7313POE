package com.example.poe.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {

    // ---------------- USERS ----------------

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query(
        "SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1"
    )
    suspend fun loginUser(
        username: String,
        password: String
    ): UserEntity?

    // ---------------- EXPENSES ----------------

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE category = :categoryName")
    suspend fun getExpensesByCategory(categoryName: String): List<ExpenseEntity>

    @Query("SELECT SUM(amount) as total FROM expenses WHERE category = :categoryName AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalByCategory(categoryName: String, startDate: String, endDate: String): Double?

    // ---------------- CATEGORIES ----------------

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>
}