package com.example.fieldwise.data.repository

import com.example.fieldwise.data.local.entities.UserProgress
import com.example.fieldwise.data.local.dao.UserProgressDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProgressRepository(private val userProgressDao: UserProgressDao) {

    //  Update and Save the user progress in specific row
    suspend fun saveUserProgress(
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
        val rowsUpdated = userProgressDao.updateUserProgress(userProgress)
        println("Rows updated: $rowsUpdated")

    }

    suspend fun getUserProgress(username: String, course: String, language: String): UserProgress? = withContext(Dispatchers.IO) {
        userProgressDao.getUserProgress(username, course, language)
    }
}
