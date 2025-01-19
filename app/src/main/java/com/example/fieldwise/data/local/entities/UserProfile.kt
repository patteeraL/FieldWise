package com.example.fieldwise.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_profile"
)
data class UserProfile(
    @PrimaryKey val username: String,
    val selectedCourse: String,
    val preferredLanguage: String,
    val notificationsEnabled: Boolean,
    val dailyGoal: String
)



