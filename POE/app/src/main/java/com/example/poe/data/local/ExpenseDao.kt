package com.example.poe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

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

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    // ---------------- CATEGORIES ----------------

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>
}