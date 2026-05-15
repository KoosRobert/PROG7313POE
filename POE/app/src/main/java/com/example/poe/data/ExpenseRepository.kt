package com.example.poe.data

import com.example.poe.data.local.ExpenseDao
import com.example.poe.data.local.ExpenseEntity
import com.example.poe.data.local.CategoryEntity
import com.example.poe.data.local.BudgetGoalEntity
import kotlinx.coroutines.flow.first

/**
 * Repository class - Mediates between ViewModel and Data Access Object (DAO)
 * Handles all data operations for the app
 */
class ExpenseRepository(private val expenseDao: ExpenseDao) {

    // ========== EXPENSE OPERATIONS ==========

    suspend fun insertExpense(expense: ExpenseEntity) = expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: ExpenseEntity) = expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)

    // Returns all expenses as List (converted from Flow)
    suspend fun getAllExpenses(): List<ExpenseEntity> = expenseDao.getAllExpenses().first()

    // Filter expenses by date range
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity> =
        expenseDao.getExpensesBetweenDates(startDate, endDate).first()

    // Get total spent in a specific category
    suspend fun getTotalByCategory(categoryName: String, startDate: String, endDate: String): Double? =
        expenseDao.getTotalByCategory(categoryName, startDate, endDate)

    // ========== CATEGORY OPERATIONS ==========

    suspend fun insertCategory(category: CategoryEntity) = expenseDao.insertCategory(category)

    suspend fun getAllCategories(): List<CategoryEntity> = expenseDao.getAllCategories().first()

    // ========== BUDGET GOAL OPERATIONS ==========

    suspend fun insertBudgetGoal(budgetGoal: BudgetGoalEntity) = expenseDao.insertBudgetGoal(budgetGoal)

    suspend fun updateBudgetGoal(budgetGoal: BudgetGoalEntity) = expenseDao.updateBudgetGoal(budgetGoal)

    suspend fun deleteBudgetGoal(budgetGoal: BudgetGoalEntity) = expenseDao.deleteBudgetGoal(budgetGoal)

    // Get budget goal for specific month
    suspend fun getBudgetGoalForMonth(month: String): BudgetGoalEntity? =
        expenseDao.getBudgetGoalByMonth(month).first()

    suspend fun getAllBudgetGoals(): List<BudgetGoalEntity> = expenseDao.getAllBudgetGoals().first()
}