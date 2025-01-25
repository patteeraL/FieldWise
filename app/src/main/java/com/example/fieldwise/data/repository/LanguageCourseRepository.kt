package com.example.fieldwise.data.repository

import android.util.Log
import com.example.fieldwise.data.local.entities.Course
import com.example.fieldwise.data.local.dao.CourseDao
import com.example.fieldwise.data.local.entities.Language
import com.example.fieldwise.data.local.entities.LanguageCourse
import com.example.fieldwise.data.local.dao.LanguageCourseDao
import com.example.fieldwise.data.local.dao.LanguageDao

class LanguageCourseRepository(private val LanguageCourseDao: LanguageCourseDao, private val LanguageDao: LanguageDao, private val CourseDao: CourseDao) {


    // Get course from language ['Geo','CS']
    suspend fun getCoursesFromLanguage(languageName: String): List<String> {
        return LanguageCourseDao.getCoursesFromLanguage(languageName)
    }

    // Get mapping of all language courses
    suspend fun getLanguagesCourses(): List<LanguageCourse> {
        return LanguageCourseDao.getLanguagesCourses()
    }

    // Insert a new language-course mapping
    suspend fun insertLanguageCourse(username: String, languageName: String, courseName: String) {
        LanguageDao.insertLanguage(
            Language(
                languageName = languageName
            )
        )
        CourseDao.insertCourse(
            Course(
                courseName = courseName
            )
        )
        LanguageCourseDao.insertLanguageCourse(
            LanguageCourse(
                languageName = languageName,
                courseName = courseName
            )
        )
    }

    suspend fun deleteLanguageCourse(username: String, languageName: String, courseName: String) {

        val courseCount = LanguageCourseDao.countCoursesFromLanguage(languageName)
        if (courseCount == 0) {
            // If no other courses exist, delete the language
            LanguageDao.deleteLanguage(
                Language(languageName = languageName)
            )
        }

        CourseDao.deleteCourse(
            Course(
                courseName = courseName
            )
        )

        LanguageCourseDao.deleteLanguageCourse(
            LanguageCourse(
                languageName = languageName,
                courseName = courseName
            )
        )


    }

}