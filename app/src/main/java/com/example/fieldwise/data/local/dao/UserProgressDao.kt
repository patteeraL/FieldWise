package com.example.fieldwise.data.local.dao

import androidx.room.*
import com.example.fieldwise.data.local.entities.UserProgress

@Dao
interface UserProgressDao {

    // Get user progress by username, courseName and languageName
    @Query("SELECT * FROM user_progress WHERE username = :username AND courseName = :courseName AND languageName = :languageName")
    suspend fun getUserProgress(username: String, courseName: String, languageName: String): UserProgress?

    // Update the user progress by the UserProgress
    @Update
    suspend fun updateUserProgress(userProgress: UserProgress)

    // Insert the user progress by the UserProgress
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(userProgress: UserProgress)

}
