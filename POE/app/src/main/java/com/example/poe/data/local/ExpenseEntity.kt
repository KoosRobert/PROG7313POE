package com.example.poe.data.local
<<<<<<< HEAD

=======
//Expense entity for storing all expense transactions
>>>>>>> main
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

<<<<<<< HEAD
=======
    val startTime: String,

    val endTime: String,

>>>>>>> main
    val photoUri: String? = null
)