package com.example.poe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_goals")
data class BudgetGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val month: Int,
    val year: Int,
    val minAmount: Double,
    val maxAmount: Double
)