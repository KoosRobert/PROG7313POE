package com.example.poe
//ViewModel for lifecycle-aware data management

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.poe.data.ExpenseRepository
import com.example.poe.data.local.AppDatabase
import com.example.poe.data.local.BudgetGoalEntity
import com.example.poe.data.local.CategoryEntity
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ExpenseRepository(database)
    }

    // ========== EXPENSE OPERATIONS ==========

    fun insertExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: ExpenseEntity) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }

    suspend fun getAllExpenses(): List<ExpenseEntity> {
        return repository.getAllExpenses()
    }

    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity> {
        return repository.getExpensesBetweenDates(startDate, endDate)
    }

    suspend fun getTotalByCategory(categoryName: String, startDate: String, endDate: String): Double? {
        return repository.getTotalByCategory(categoryName, startDate, endDate)
    }

    // ========== CATEGORY OPERATIONS ==========

    fun insertCategory(category: CategoryEntity) = viewModelScope.launch {
        repository.insertCategory(category)
    }

    suspend fun getAllCategories(): List<CategoryEntity> {
        return repository.getAllCategories()
    }

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

    suspend fun getBudgetGoalForMonth(month: Int, year: Int): BudgetGoalEntity? {
        return repository.getBudgetGoalForMonth(month, year)
    }

    suspend fun getAllBudgetGoals(): List<BudgetGoalEntity> {
        return repository.getAllBudgetGoals()
    }
}