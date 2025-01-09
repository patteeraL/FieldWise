package com.example.fieldwise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM user_profile WHERE username = :username")
    suspend fun getUserProfile(username: String): UserProfile?

    @Query("SELECT * FROM user_profile")
    suspend fun getAllUserProfiles(): List<UserProfile>

    @Update
    suspend fun updateUserProfile(userProfile: UserProfile)
}


