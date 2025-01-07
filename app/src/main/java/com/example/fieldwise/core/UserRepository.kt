package com.example.fieldwise.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userProfileDao: UserProfileDao) {

    // Save or update the entire user profile
    suspend fun saveUserProfile(userProfile: UserProfile) = withContext(Dispatchers.IO) {
        userProfileDao.insertUserProfile(userProfile)
    }

    // Get user profile by username
    suspend fun getUserProfile(username: String): UserProfile? = withContext(Dispatchers.IO) {
        userProfileDao.getUserProfile(username)
    }

    // Save or update the globalUsername
    suspend fun saveGlobal(globalUsername: String, globalLanguage: String, globalCourse: String) = withContext(Dispatchers.IO) {
        val existingProfile = userProfileDao.getUserProfile(globalUsername)
        if (existingProfile == null) {
            userProfileDao.insertUserProfile(
                UserProfile(
                    username = globalUsername,
                    selectedCourse = globalCourse,
                    preferredLanguage = globalLanguage,
                    dailyGoal = 0,
                    notificationsEnabled = false
                )
            )
        } else {
            userProfileDao.updateUserProfile(existingProfile.copy(username = globalUsername, selectedCourse = globalCourse, preferredLanguage = globalLanguage))
        }
    }

    // Retrieve the globalUsername (most recently saved)
    suspend fun getSavedGlobalUsername(): String? = withContext(Dispatchers.IO) {
        val allProfiles = userProfileDao.getAllUserProfiles()
        return@withContext if (allProfiles.isNotEmpty()) {
            allProfiles.lastOrNull()?.username // Retrieve the last saved username
        } else null
    }

    // Retrieve the most recently saved preferred language
    suspend fun getSavedLanguage(): String? = withContext(Dispatchers.IO) {
        val allProfiles = userProfileDao.getAllUserProfiles()
        return@withContext if (allProfiles.isNotEmpty()) {
            allProfiles.lastOrNull()?.preferredLanguage // Retrieve the last saved username
        } else null
    }

    // Retrieve the most recently saved selected course
    suspend fun getSavedCourse(): String? = withContext(Dispatchers.IO) {
        val allProfiles = userProfileDao.getAllUserProfiles()
        return@withContext if (allProfiles.isNotEmpty()) {
            allProfiles.lastOrNull()?.selectedCourse // Retrieve the last saved username
        } else null
    }
}
