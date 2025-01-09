package com.example.fieldwise.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserProgress::class, UserProfile::class], version = 1)
@TypeConverters(Converters::class) // Register the converters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userProgressDao(): UserProgressDao
}
