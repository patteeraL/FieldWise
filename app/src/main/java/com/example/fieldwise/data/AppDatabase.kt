package com.example.fieldwise.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserProgress::class, UserProfile::class], version = 1)
@TypeConverters(Converters::class) // Register the converters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userProgressDao(): UserProgressDao
}

//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        // Add columns for courses and languages as JSON strings
//        database.execSQL("ALTER TABLE user_profile ADD COLUMN courses TEXT NOT NULL DEFAULT '[]'")
//        database.execSQL("ALTER TABLE user_profile ADD COLUMN languages TEXT NOT NULL DEFAULT '[]'")
//    }
//}

