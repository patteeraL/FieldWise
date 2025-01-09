package com.example.fieldwise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LanguageDao {
    @Insert
    suspend fun insertLanguage(language: Language)

    @Query("SELECT * FROM languages")
    suspend fun getAllLanguages(): List<Language>
}

@Dao
interface CourseDao {
    @Insert
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<Course>
}

@Dao
interface LanguageCourseDao {
    @Insert
    suspend fun insertLanguageCourse(languageCourse: LanguageCourse)

    @Query("SELECT courseName FROM language_course WHERE languageName = :languageName")
    suspend fun getCoursesForLanguage(languageName: String): List<String>
}