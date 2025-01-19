package com.example.fieldwise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "user_progress",
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
data class UserProgress(
    val username: String,
    val courseName: String,
    val languageName: String,
    val vocabProgress1: Float = 0f,
    val listeningProgress1: Float = 0f,
    val speakingProgress1: Float = 0f,
    val convoProgress1: Float = 0f,
    val vocabProgress2: Float = 0f,
    val listeningProgress2: Float = 0f,
    val speakingProgress2: Float = 0f,
    val convoProgress2: Float = 0f
)
