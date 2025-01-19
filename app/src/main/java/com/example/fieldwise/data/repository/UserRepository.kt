package com.example.fieldwise.data.repository

import com.example.fieldwise.data.local.dao.UserProfileDao
import com.example.fieldwise.data.local.entities.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class UserRepository(private val userProfileDao: UserProfileDao) {

    // Save and update the user profile
    suspend fun saveUserProfile(
        username: String,
        selectedCourse: String,
        preferredLanguage: String,
        dailyGoal: String,
        notificationsEnabled: Boolean
        ) {
        val userProfile = UserProfile(
            username = username,
            selectedCourse = selectedCourse,
            preferredLanguage = preferredLanguage,
            dailyGoal = dailyGoal,
            notificationsEnabled = notificationsEnabled
        )
        userProfileDao.updateUserProfile(userProfile)
        }

    // Get user profile by username
    suspend fun getUserProfile(username: String): UserProfile? = withContext(Dispatchers.IO) {
        userProfileDao.getUserProfile(username)
    }

    // Delete the user profile by username
    suspend fun deleteUserProfile(
        username: String
    ){
        val userProfile = getUserProfile(username)
        userProfileDao.deleteUserProfile(userProfile)
    }











}
