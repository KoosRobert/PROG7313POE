package com.example.poe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Room Database – BudgetBuddy
 *
 * Version history:
 *   1 → initial schema
 *   2 → added income / minGoal / maxGoal columns to users table
 *   3 → no schema change; ExpenseDao updated (deleteCategory added)
 *       Version bump forces Room to revalidate the schema cleanly.
 */
@Database(
    entities = [
        UserEntity::class,
        ExpenseEntity::class,
        CategoryEntity::class,
        BudgetGoalEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // ── Migration 2 → 3 (schema unchanged, just a version bump) ──
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // No DDL changes needed; Room re-checks the schema.
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}