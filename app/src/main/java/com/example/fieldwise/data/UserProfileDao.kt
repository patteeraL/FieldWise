package com.example.fieldwise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao //Data Access Object
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)  // do not replace already existing
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM user_profile WHERE username = :username LIMIT 1")
    suspend fun getUserProfile(username: String): UserProfile?
}