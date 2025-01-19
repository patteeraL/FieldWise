package com.example.fieldwise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "language_course",
    primaryKeys = ["username","languageName", "courseName"],
    foreignKeys = [
        ForeignKey(
            entity = UserProfile::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        ),
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
    val username: String,
    val languageName: String,
    val courseName: String
)


