package com.example.fieldwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val username: String,
    val selectedCourse: String,
    val preferredLanguage: String,
    val dailyGoal: Int,
    val notificationsEnabled: Boolean,
    val streak: Int = 0
)
