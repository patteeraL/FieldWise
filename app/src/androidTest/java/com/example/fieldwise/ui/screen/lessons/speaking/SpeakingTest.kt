package com.example.fieldwise.ui.screen.lessons.speaking

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.example.fieldwise.navigation.NavigationWrapper
import org.junit.Rule
import org.junit.Test

class SpeakingTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSpeaking() {
        // Ensure the UI content is set before performing any interactions
        composeTestRule.setContent {
            NavigationWrapper(isFirstTime = false)  // Initialize your UI
        }

        // Wait for the "Lesson 2" button to appear and perform the interaction
        composeTestRule.onNodeWithText("Lesson 2").performClick()
        composeTestRule.onNodeWithText("Resume").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("Speaking").assertIsDisplayed().performClick()


        composeTestRule.waitForIdle()

        // Use UiAutomator to handle the permission dialog
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Wait for the "Only this time" button to appear
        var allowButton: UiObject2? = null

            allowButton = uiDevice.findObject(By.text("Only this time"))
            // If the "Allow" button is found and is clickable, click it
            if (allowButton != null && allowButton.isClickable) {
                allowButton.click()
                Log.d("SpeakingTest", "Permission granted successfully")
            } else {
                Log.w("SpeakingTest", "Permission dialog not found; assuming it's already granted")
            }


        // Wait for the permission dialog to disappear and the main screen to stabilize
        composeTestRule.waitForIdle()

        // Retry checking if the Speaking screen is displayed
        try {
            // Wait for the SpeakingScreen to appear after the dialog is dismissed
            composeTestRule.waitUntil(timeoutMillis = 5000) {
                try {
                    composeTestRule.onNodeWithTag("SpeakingScreen").assertIsDisplayed()
                    true
                } catch (_: AssertionError) {
                    false
                }
            }

            Log.d("SpeakingTest", "SpeakingScreen has appeared after permission granted")
        } catch (e: AssertionError) {
            Log.e("SpeakingTest", "SpeakingScreen not displayed; check if permission flow is correct")
        }
        // Wait for the permission dialog to disappear and the main screen to stabilize
        composeTestRule.waitForIdle()

        // Interact with the Speaking screen
        composeTestRule.onNodeWithTag("SoundButton").performClick()
        composeTestRule.onNodeWithTag("QuestionText").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MicButton").performClick()
        Log.d("SpeakingTest", "Perform Click on MicButton")
        composeTestRule.onNodeWithTag("MicButton").performClick()
        composeTestRule.onNodeWithText("Processing your answer...").assertIsDisplayed()

    }
}