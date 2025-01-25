package com.example.fieldwise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey


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