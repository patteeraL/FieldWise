package com.example.fieldwise.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.fieldwise.data.local.entities.LanguageCourse

@Dao
interface LanguageCourseDao {

    // Get course from language ['Geo','CS']
    @Query("""
        SELECT c.courseName 
        FROM courses c 
        INNER JOIN language_course lc ON c.courseName = lc.courseName 
        WHERE lc.languageName = :languageName
    """)
    fun getCoursesFromLanguage(languageName: String): List<String>

    // Get mapping of all language courses
    @Transaction
    @Query("""
        SELECT l.languageName, c.courseName
        FROM languages l
        INNER JOIN language_course lc ON l.languageName = lc.languageName
        INNER JOIN courses c ON c.courseName = lc.courseName
    """)
    fun getLanguagesCourses(): List<LanguageCourse>

    // Insert Language and course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLanguageCourse(languageCourse: LanguageCourse)

    // Delete Language and course
    @Delete
    fun deleteLanguageCourse(course: LanguageCourse)

    // Get count of courses from languageName
    @Query("SELECT COUNT(*) FROM language_course WHERE languageName = :languageName")
    suspend fun countCoursesFromLanguage(languageName: String): Int
}