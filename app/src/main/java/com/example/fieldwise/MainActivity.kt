package com.example.fieldwise

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fieldwise.navigation.NavigationWrapper
import com.example.fieldwise.ui.theme.FieldWiseTheme

class MainActivity : ComponentActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val FIRST_TIME_KEY = "firstTime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settings: SharedPreferences = getSharedPreferences(PREFS_NAME, 0)
        val firstTime: Boolean = settings.getBoolean(FIRST_TIME_KEY, true)

        if (firstTime) {
            // This is the first time the app is opened

            setContent {
                FieldWiseTheme {
                    NavigationWrapper(isFirstTime = true) // Llama al wrapper de navegación
                }
            }
            // Set the flag to false
            val editor: SharedPreferences.Editor = settings.edit()
            editor.putBoolean(FIRST_TIME_KEY, false)
            editor.apply()

        } else {
            // The app has been opened before
            setContent {
                FieldWiseTheme {
                    NavigationWrapper(isFirstTime = false) // Llama al wrapper de navegación
                }
            }
        }


    }
}