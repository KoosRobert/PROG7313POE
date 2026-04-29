package com.example.poe.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // ---------------- USERS ----------------

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query(
        """
        SELECT * FROM users
        WHERE (username = :loginInput OR email = :loginInput)
        AND password = :password
        LIMIT 1
        """
    )
    suspend fun loginUser(
        loginInput: String,
        password: String
    ): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    // ---------------- EXPENSES ----------------

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesBetweenDates(startDate: String, endDate: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE category = :categoryName")
    suspend fun getExpensesByCategory(categoryName: String): List<ExpenseEntity>

    @Query("SELECT SUM(amount) as total FROM expenses WHERE category = :categoryName AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalByCategory(categoryName: String, startDate: String, endDate: String): Double?

    // ---------------- CATEGORIES ----------------

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    // ---------------- BUDGET GOALS ----------------

    @Insert
    suspend fun insertBudgetGoal(budgetGoal: BudgetGoalEntity)

    @Update
    suspend fun updateBudgetGoal(budgetGoal: BudgetGoalEntity)

    @Delete
    suspend fun deleteBudgetGoal(budgetGoal: BudgetGoalEntity)

    @Query("SELECT * FROM budget_goals WHERE month = :month")
    fun getBudgetGoalByMonth(month: String): Flow<BudgetGoalEntity?>

    @Query("SELECT * FROM budget_goals")
    fun getAllBudgetGoals(): Flow<List<BudgetGoalEntity>>
}