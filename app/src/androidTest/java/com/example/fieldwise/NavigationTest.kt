package com.example.fieldwise

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.fieldwise.navigation.NavigationWrapper
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun basicNavigationTest() { //test navigation from SplashScreen to HomeScreen
        composeTestRule.setContent {
            NavigationWrapper()
        }
        try {
            composeTestRule.onNodeWithText("SplashScreen").assertExists()
            Log.d("NavigationTest", "SPLASHSCREEN FOUND")

            composeTestRule.onNodeWithText("Continue")
                .performClick() // action of navigate Splash > Loading
            composeTestRule.onNodeWithText("LoadingScreen").assertExists()
            Log.d("NavigationTest", "LOADINGSCREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("UsernameScreen").assertExists()
            Log.d("NavigationTest", "USERNAMECREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("SetDailyGoalScreen").assertExists()
            Log.d("NavigationTest", "SETDAILYGOALSCREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("EnableNotifyScreen").assertExists()
            Log.d("NavigationTest", "ENABLENOTIFYSCREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("CourseManageScreen").assertExists()
            Log.d("NavigationTest", "COURSEMANAGESCREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("CompleteScreen").assertExists()
            Log.d("NavigationTest", "COMPLETESCREEN FOUND")

            composeTestRule.onNodeWithText("Continue").performClick()
            composeTestRule.onNodeWithText("HomeScreen").assertExists()
            Log.d("NavigationTest", "HOMESCREEN FOUND")

        } catch (error: AssertionError) {
            Log.e("NavigationTest", "TEST FAILED: ${error.message}")
            throw error
        }



    }



}

