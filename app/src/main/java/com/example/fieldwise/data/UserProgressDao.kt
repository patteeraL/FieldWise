package com.example.fieldwise.data

import androidx.room.*

@Dao
interface UserProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(userProgress: UserProgress)

    @Query("SELECT * FROM user_progress WHERE username = :username AND course = :course AND language = :language")
    suspend fun getUserProgress(username: String, course: String, language: String): UserProgress?

    @Update
    suspend fun updateUserProgress(userProgress: UserProgress)

    @Delete
    suspend fun deleteUserProgress(userProgress: UserProgress)

    @Query("SELECT * FROM user_progress WHERE username = :username")
    suspend fun getAllUserProgressForUser(username: String): List<UserProgress>
}
