//BudgetGoalDao for CRUD operations on budget goals
package com.example.poe.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetGoalDao {

    @Insert
    suspend fun insertBudgetGoal(goal: BudgetGoalEntity)

    @Update
    suspend fun updateBudgetGoal(goal: BudgetGoalEntity)

    @Delete
    suspend fun deleteBudgetGoal(goal: BudgetGoalEntity)

    @Query("SELECT * FROM budget_goals WHERE month = :month AND year = :year LIMIT 1")
    suspend fun getBudgetGoalForMonth(month: Int, year: Int): BudgetGoalEntity?

    @Query("SELECT * FROM budget_goals ORDER BY year DESC, month DESC")
    suspend fun getAllBudgetGoals(): List<BudgetGoalEntity>
}