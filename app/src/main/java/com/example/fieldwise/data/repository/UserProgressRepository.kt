package com.example.fieldwise.data.repository

import com.example.fieldwise.data.local.entities.UserProgress
import com.example.fieldwise.data.local.dao.UserProgressDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProgressRepository(private val userProgressDao: UserProgressDao) {
    suspend fun insertUserProgress(
        username: String,
        course: String,
        language: String,
        vocabProgress1: Float,
        listeningProgress1: Float,
        speakingProgress1: Float,
        convoProgress1: Float,
        vocabProgress2: Float,
        listeningProgress2: Float,
        speakingProgress2: Float,
        convoProgress2: Float
    ) {
        val userProgress = UserProgress(
            username,
            course,
            language,
            vocabProgress1, // new progress
            listeningProgress1, // new progress
            speakingProgress1, // new progress
            convoProgress1, // new progress
            vocabProgress2, // new progress
            listeningProgress2, // new progress
            speakingProgress2, // new progress
            convoProgress2) // new progress
        val rowsInserted = userProgressDao.insertUserProgress(userProgress)
        println("Rows inserted: $rowsInserted")
    }

    suspend fun updateUserProgress(
        username: String,
        course: String,
        language: String,
        vocabProgress1: Float?,
        listeningProgress1: Float?,
        speakingProgress1: Float?,
        convoProgress1: Float?,
        vocabProgress2: Float?,
        listeningProgress2: Float?,
        speakingProgress2: Float?,
        convoProgress2: Float?
    ) {
        val currentUserProgress = userProgressDao.getUserProgress(username, course, language)
        if (currentUserProgress != null) {
            val updatedUserProgress = currentUserProgress.copy(
                vocabProgress1 = vocabProgress1 ?: currentUserProgress.vocabProgress1,
                listeningProgress1 = listeningProgress1 ?: currentUserProgress.listeningProgress1,
                speakingProgress1 = speakingProgress1 ?: currentUserProgress.speakingProgress1,
                convoProgress1 = convoProgress1 ?: currentUserProgress.convoProgress1,
                vocabProgress2 = vocabProgress2 ?: currentUserProgress.vocabProgress2,
                listeningProgress2 = listeningProgress2 ?: currentUserProgress.listeningProgress2,
                speakingProgress2 = speakingProgress2 ?: currentUserProgress.speakingProgress2,
                convoProgress2 = convoProgress2 ?: currentUserProgress.convoProgress2
            )

            // Update only the modified fields
            val rowsUpdated = userProgressDao.updateUserProgress(updatedUserProgress)
            println("Rows updated: $rowsUpdated")
        } else {
            println("UserProgress not found for $username, $course, $language")
        }
    }


    suspend fun getUserProgress(username: String, course: String, language: String): UserProgress? = withContext(Dispatchers.IO) {
        userProgressDao.getUserProgress(username, course, language)
    }
}
