package com.example.fieldwise

import SplashScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.navigation.NavigationWrapper
import com.example.fieldwise.navigation.Routes
import org.junit.Rule
import org.junit.Test

class StarterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSplashScreenToHomeScreen() {
        // Set up the Compose content using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper()
        }

        // Check if the SplashScreen is displayed initially
        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()

        // Click to go to loading screen
        composeTestRule.onNodeWithText("GET STARTED").performClick()

        // Check if the LoadingScreen is displayed
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()

        // Wait until the LoadingScreen disappears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("LoadingScreen").assertDoesNotExist()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "LoadingScreen is still displayed")
                false
            }
        }

        // Check if the UsernameScreen is displayed
        composeTestRule.onNodeWithTag("UsernameScreen").assertIsDisplayed()

        // Enter text and proceed
        composeTestRule.onNodeWithTag("OutlinedTextField").performTextInput("FieldWiseADMIN")
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the SetDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("SetDailyGoalScreen").assertIsDisplayed()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "SetDailyGoalScreen is not displayed yet")
                false
            }
        }

        // Select Daily Goal
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the EnableNotifyScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("EnableNotifyScreen").assertIsDisplayed()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "EnableNotifyScreen is not displayed yet")
                false
            }
        }

        // Allow notify
        composeTestRule.onNodeWithTag("AllowButton").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the CourseManageScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("CourseManageScreen").assertIsDisplayed()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "CourseManageScreen is not displayed yet")
                false
            }
        }

        // Select Course
        composeTestRule.onNodeWithText("Computer Science").performClick()
        composeTestRule.onNodeWithText("English").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the CompleteScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("CompleteScreen").assertIsDisplayed()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "CompleteScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("ConfirmButton").performClick()

        // Wait until the HomeScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("HomeScreen").assertIsDisplayed()
                true
            } catch (e: AssertionError) {
                Log.d("SplashScreenTest", "HomeScreen is not displayed yet")
                false
            }
        }
    }
}