package com.example.fieldwise.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore("user_preferences")

object PreferencesKeys {
    val FIRST_TIME_USER = booleanPreferencesKey("first_time_user")
}

suspend fun setFirstTimeUser(context: Context, isFirstTime: Boolean) {
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.FIRST_TIME_USER] = isFirstTime
    }
}

fun isFirstTimeUser(context: Context): Boolean {
    return runBlocking {
        val preferences = context.dataStore.data.first()
        preferences[PreferencesKeys.FIRST_TIME_USER] ?: true
    }
}
