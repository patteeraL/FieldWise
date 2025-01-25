package com.example.fieldwise.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.fieldwise.data.local.entities.UserProfile

@Dao
interface UserProfileDao {

    // Get all user profile
    @Query("""
        SELECT * 
        FROM user_profile
    """)
    fun getAllUserProfiles(): List<UserProfile>

    // Get The user profile from username
    @Query("""
        SELECT * 
        FROM user_profile 
        WHERE username = :username
    """)
    fun getUserProfile(username: String): UserProfile

    // Get only user selectedCourse from username
    @Query("""
        SELECT selectedCourse 
        FROM user_profile 
        WHERE username = :username
    """)
    fun getUserSelectedCourse(username: String): String

    // Get only user preferredLanguage from username
    @Query("""
        SELECT preferredLanguage 
        FROM user_profile 
        WHERE username = :username
    """)
    fun getUserPreferredLanguage(username: String): String

    // Get only user notificationsEnabled from username
    @Query("""
        SELECT notificationsEnabled 
        FROM user_profile 
        WHERE username = :username
    """)
    fun getUserNotificationsEnabled(username: String): Boolean

    // Get only user dailyGoal from username
    @Query("""
        SELECT dailyGoal 
        FROM user_profile 
        WHERE username = :username
    """)
    fun getUserDailyGoal(username: String): String

    // Update the user profile with UserProfile
    @Update
    suspend fun updateUserProfile(userProfile: UserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    // Delete the user profile with UserProfile
    @Delete
    suspend fun deleteUserProfile(userProfile: UserProfile?)
}


