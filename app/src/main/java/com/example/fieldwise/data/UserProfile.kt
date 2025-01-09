package com.example.fieldwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val username: String,
    val selectedCourse: String,
    val courses: List<String> = emptyList(),
    val preferredLanguage: String,
    val languages: List<String> = emptyList(),
    val notificationsEnabled: Boolean,
    val streak: Int = 0,
    val dailyGoal: String
)


