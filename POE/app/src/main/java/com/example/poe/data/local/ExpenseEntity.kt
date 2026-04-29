package com.example.poe.data.local
//User entity for authentication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,

    val description: String,

    val category: String,

    val date: String,

    val startTime: String,

    val endTime: String,

    val photoUri: String? = null
)