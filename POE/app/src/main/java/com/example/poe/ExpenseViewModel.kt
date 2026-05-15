package com.example.poe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.poe.data.ExpenseRepository
import com.example.poe.data.local.AppDatabase
import com.example.poe.data.local.BudgetGoalEntity
import com.example.poe.data.local.CategoryEntity
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.launch

/**
 * ViewModel for Expense-related UI data
 * Survives configuration changes and provides lifecycle-aware data
 */
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository

    init {
        // Initialize database and repository
        val database = AppDatabase.getDatabase(application)
        val expenseDao = database.expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    // ========== EXPENSE OPERATIONS ==========
    // These functions are called from UI to manage expense data

    fun insertExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }

    suspend fun getAllExpenses(): List<ExpenseEntity> = repository.getAllExpenses()

    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity> =
        repository.getExpensesBetweenDates(startDate, endDate)

    suspend fun getTotalByCategory(categoryName: String, startDate: String, endDate: String): Double? =
        repository.getTotalByCategory(categoryName, startDate, endDate)

    // ========== CATEGORY OPERATIONS ==========

    fun insertCategory(category: CategoryEntity) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    suspend fun getAllCategories(): List<CategoryEntity> = repository.getAllCategories()

    // ========== BUDGET GOAL OPERATIONS ==========

    fun insertBudgetGoal(goal: BudgetGoalEntity) = viewModelScope.launch {
        repository.insertBudgetGoal(goal)
    }

    fun updateBudgetGoal(goal: BudgetGoalEntity) = viewModelScope.launch {
        repository.updateBudgetGoal(goal)
    }

    fun deleteBudgetGoal(goal: BudgetGoalEntity) = viewModelScope.launch {
        repository.deleteBudgetGoal(goal)
    }

    suspend fun getBudgetGoalForMonth(month: String): BudgetGoalEntity? =
        repository.getBudgetGoalForMonth(month)

    suspend fun getAllBudgetGoals(): List<BudgetGoalEntity> = repository.getAllBudgetGoals()
}