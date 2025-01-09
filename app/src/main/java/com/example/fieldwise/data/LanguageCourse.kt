package com.example.fieldwise.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey val languageName: String
)

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey val courseName: String
)

@Entity(
    tableName = "language_course",
    primaryKeys = ["languageName", "courseName"],
    foreignKeys = [
        ForeignKey(
            entity = Language::class,
            parentColumns = ["languageName"],
            childColumns = ["languageName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["courseName"],
            childColumns = ["courseName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LanguageCourse(
    val languageName: String,
    val courseName: String
)

