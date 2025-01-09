package com.example.fieldwise.core

import com.example.fieldwise.data.UserProgress
import com.example.fieldwise.data.UserProgressDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProgressRepository(private val userProgressDao: UserProgressDao) {

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

    ) = withContext(Dispatchers.IO) {
        val existingProgress = userProgressDao.getUserProgress(username, course, language)
        if (existingProgress == null) {
            userProgressDao.insertUserProgress(
                UserProgress(
                    username = username,
                    course = course,
                    language = language,
                    vocabProgress1 = vocabProgress1,
                    listeningProgress1 = listeningProgress1,
                    speakingProgress1 = speakingProgress1,
                    convoProgress1 = convoProgress1,
                    vocabProgress2 = vocabProgress2,
                    listeningProgress2 = listeningProgress2,
                    speakingProgress2 = speakingProgress2,
                    convoProgress2 = convoProgress2
                )
            )
        } else {
            userProgressDao.updateUserProgress(existingProgress.copy(
                vocabProgress1 = vocabProgress1,
                listeningProgress1 = listeningProgress1,
                speakingProgress1 = speakingProgress1,
                convoProgress1 = convoProgress1,
                vocabProgress2 = vocabProgress2,
                listeningProgress2 = listeningProgress2,
                speakingProgress2 = speakingProgress2,
                convoProgress2 = convoProgress2
            ))
        }
    }

    suspend fun getUserProgress(username: String, course: String, language: String): UserProgress? = withContext(Dispatchers.IO) {
        userProgressDao.getUserProgress(username, course, language)
    }
}
