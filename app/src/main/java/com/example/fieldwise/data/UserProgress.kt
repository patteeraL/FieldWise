package com.example.fieldwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val username: String,
    val course: String,
    val language: String,
    val vocabProgress1: Float,
    val listeningProgress1: Float,
    val speakingProgress1: Float,
    val convoProgress1: Float,
    val vocabProgress2: Float,
    val listeningProgress2: Float,
    val speakingProgress2: Float,
    val convoProgress2: Float
)
