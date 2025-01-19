package com.example.fieldwise.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey val languageName: String
)