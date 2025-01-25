package com.example.fieldwise.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.fieldwise.data.local.entities.Language

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLanguage(language: Language)

    @Delete
    fun deleteLanguage(language: Language)
}