package com.example.fieldwise.data.provider

import android.content.Context
import androidx.room.Room
import com.example.fieldwise.data.local.AppDatabase
//import com.example.fieldwise.data.MIGRATION_1_2
import com.example.fieldwise.data.repository.UserRepository
import com.example.fieldwise.data.repository.LanguageCourseRepository
import com.example.fieldwise.data.repository.UserProgressRepository

object DatabaseProvider {
    @Volatile
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )

                .build()
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

    fun provideLanguageCourseRepository(context: Context): LanguageCourseRepository {
        val database = provideDatabase(context)
        return LanguageCourseRepository(
            database.languageCourseDao(),
            database.languageDao(),
            database.courseDao()
        )
    }

}
