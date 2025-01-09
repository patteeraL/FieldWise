package com.example.fieldwise.core

import android.content.Context
import androidx.room.Room
import com.example.fieldwise.data.AppDatabase
import com.example.fieldwise.data.UserProgress
import com.example.fieldwise.data.UserRepository

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            database = instance
            instance
        }
    }

    fun provideUserRepository(context: Context): UserRepository {
        val database = provideDatabase(context)
        return UserRepository(database.userProfileDao())
    }

    fun provideUserProgressRepository(context: Context): UserProgressRepository {
        val database = provideDatabase(context)
        return UserProgressRepository(database.userProgressDao())
    }
}
