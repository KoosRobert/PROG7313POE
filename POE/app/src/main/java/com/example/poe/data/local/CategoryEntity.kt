package com.example.poe.data.local
//Category entity for expense classification
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String
)