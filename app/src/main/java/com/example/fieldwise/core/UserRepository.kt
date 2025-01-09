package com.example.fieldwise.data

import com.example.fieldwise.ui.screen.profile_creation.dailyGoal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class UserRepository(private val userProfileDao: UserProfileDao) {

    suspend fun saveUserProfile(
        username: String,
        courses: List<String>,
        languages: List<String>,
        selectedCourse: String,
        preferredLanguage: String,
        dailyGoal: String,
        notificationsEnabled: Boolean
        ) {
        val userProfile = UserProfile(
            username = username,
            courses = courses,  // List<String> will be converted to JSON
            languages = languages,  // List<String> will be converted to JSON
            selectedCourse = selectedCourse,
            preferredLanguage = preferredLanguage,
            dailyGoal = dailyGoal,
            notificationsEnabled = notificationsEnabled
        )

        // Insert or update in the database
        userProfileDao.insertUserProfile(userProfile)
        }


    // Get user profile by username
    suspend fun getUserProfile(username: String): UserProfile? = withContext(Dispatchers.IO) {
        userProfileDao.getUserProfile(username)
    }

    suspend fun updateUserProfile(userProfile: UserProfile) {
        userProfileDao.updateUserProfile(userProfile)
    }

    suspend fun getUserLanguages(username: String): List<String>? = withContext(Dispatchers.IO) {
        val userProfile = userProfileDao.getUserProfile(username)
        // If the profile exists, return the languages, otherwise return null
        userProfile?.languages
    }

    suspend fun getUserFields(username: String): List<String>? = withContext(Dispatchers.IO) {
        val userProfile = userProfileDao.getUserProfile(username)
        // If the profile exists, return the languages, otherwise return null
        userProfile?.courses
    }

    // Save or update the globalUsername
    suspend fun saveGlobal(globalUsername: String, globalLanguage: String, globalCourse: String) = withContext(Dispatchers.IO) {
        val existingProfile = userProfileDao.getUserProfile(globalUsername)
        if (existingProfile == null) {
            userProfileDao.insertUserProfile(
                UserProfile(
                    username = globalUsername,
                    courses = mutableListOf(), // Use a MutableList to add courses
                    languages = mutableListOf(),
                    selectedCourse = globalCourse,
                    preferredLanguage = globalLanguage,
                    dailyGoal = dailyGoal,
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
