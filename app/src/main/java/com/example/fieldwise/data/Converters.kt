package com.example.fieldwise.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(list: List<Int>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toIntList(json: String?): List<Int>? {
        return json?.let {
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(it, type)
        }
    }
}
