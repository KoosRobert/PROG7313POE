package com.example.poe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val email: String,

    val username: String,

    val password: String,

    // ---------------- NEW FIELDS ----------------

    val income: Double = 0.0,

    val minGoal: Double = 0.0,

    val maxGoal: Double = 0.0
)