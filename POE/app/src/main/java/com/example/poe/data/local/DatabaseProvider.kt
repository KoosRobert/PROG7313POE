package com.example.poe.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        if (database == null) {

            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "budget_database"
            ).build()
        }

        return database!!
    }
}