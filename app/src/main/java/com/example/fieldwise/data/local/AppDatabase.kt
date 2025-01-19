package com.example.fieldwise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fieldwise.data.local.dao.CourseDao
import com.example.fieldwise.data.local.dao.LanguageCourseDao
import com.example.fieldwise.data.local.dao.LanguageDao
import com.example.fieldwise.data.local.dao.UserProfileDao
import com.example.fieldwise.data.local.dao.UserProgressDao
import com.example.fieldwise.data.local.entities.Course
import com.example.fieldwise.data.local.entities.Language
import com.example.fieldwise.data.local.entities.LanguageCourse
import com.example.fieldwise.data.local.entities.UserProfile
import com.example.fieldwise.data.local.entities.UserProgress

@Database(entities = [UserProgress::class, UserProfile::class, Language::class, Course::class, LanguageCourse::class], version = 1)
@TypeConverters(Converters::class) // Register the converters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun languageDao(): LanguageDao
    abstract fun courseDao(): CourseDao
    abstract fun languageCourseDao(): LanguageCourseDao
}

