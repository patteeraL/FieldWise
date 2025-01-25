package com.example.fieldwise.data.repository

import com.example.fieldwise.data.local.dao.UserProfileDao
import com.example.fieldwise.data.local.entities.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String
import android.util.Log

class UserRepository(private val userProfileDao: UserProfileDao) {

    // Save and update the user profile
    suspend fun insertUserProfile(
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
        userProfileDao.insertUserProfile(userProfile)
    }

    suspend fun updateUserProfile(
        username: String,
        selectedCourse: String?,
        preferredLanguage: String?,
        dailyGoal: String?,
        notificationsEnabled: Boolean?
    ) {
        val currentUserProfile = userProfileDao.getUserProfile(username)
            val updatedUserProfile = currentUserProfile.copy(
                selectedCourse = selectedCourse ?: currentUserProfile.selectedCourse,
                preferredLanguage = preferredLanguage ?: currentUserProfile.preferredLanguage,
                dailyGoal = dailyGoal ?: currentUserProfile.dailyGoal,
                notificationsEnabled = notificationsEnabled ?: currentUserProfile.notificationsEnabled
            )
            val rowsUpdated = userProfileDao.updateUserProfile(updatedUserProfile)
            println("Rows updated: $rowsUpdated")
    }

    // Get user profile by username
    suspend fun getUserProfile(username: String): UserProfile? = withContext(Dispatchers.IO) {
        userProfileDao.getUserProfile(username)
    }

    // Retrieve the globalUsername (most recently saved)
    suspend fun getSavedGlobalUsername(): String? = withContext(Dispatchers.IO) {
        val allProfiles = userProfileDao.getAllUserProfiles()
        Log.d("allProfiles","$allProfiles")
        return@withContext if (allProfiles.isNotEmpty()) {
            allProfiles.lastOrNull()?.username // Retrieve the last saved username
        } else null
    }

    // Delete the user profile by username
    suspend fun deleteUserProfile(
        username: String
    ){
        val userProfile = getUserProfile(username)
        userProfileDao.deleteUserProfile(userProfile)
    }
}
