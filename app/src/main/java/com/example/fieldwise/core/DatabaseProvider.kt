package com.example.fieldwise.core

import android.content.Context
import androidx.room.Room
import com.example.fieldwise.data.AppDatabase

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
}