package com.example.fieldwise.ui.screen.lessons.listening

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.example.fieldwise.NavigationWrapper
import org.junit.Rule
import org.junit.Test

class ListeningTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testListening() {
        // Set up the Compose content using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper(isFirstTime = false)
        }
        // Navigate to Listening Screen
        composeTestRule.onNodeWithText("Lesson 2").performClick()
        composeTestRule.onNodeWithText("Resume").performClick()
        composeTestRule.onNodeWithText("Listening").performClick()

        // Wait until the Listening1 Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("Listen1Screen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if images are displayed or not
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onNodeWithTag("Discussion").assertIsDisplayed()
                composeTestRule.onNodeWithTag("SoundButton").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content1").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content2").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content3").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content4").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if text to speech button
        composeTestRule.onNodeWithTag("SoundButton").performClick()

        // Test Discussion
        composeTestRule.onNodeWithTag("DropDownButton").performClick()
        composeTestRule.onNodeWithTag("CommentField").performTextInput("Good Question!")
        composeTestRule.onNodeWithTag("SendButton").performClick()
        composeTestRule.onNodeWithTag("Listen1Screen").performScrollToNode(hasText("Good Question!"))
        composeTestRule.onNodeWithTag("DropDownButton").performClick()

        val actions1 = listOf(
            {
                composeTestRule.onNodeWithTag("Content1").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Content2").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Content3").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Content4").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            }
        )

        // Execute actions sequentially until Listen1Screen no longer exists
        for (action in actions1) {
            action() // Run the action
            composeTestRule.waitForIdle() // Wait for the UI to stabilize

            try {
                composeTestRule.onNodeWithTag("Listen1Screen").assertDoesNotExist()
                break // Exit the loop if Listen1Screen does not exist
            } catch (_: AssertionError) {
                // Continue to the next action if Listen1Screen still exists
            }
        }

        // Wait until the Vocab2 Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("Listen2Screen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if images are displayed or not
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onNodeWithTag("Discussion").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button01").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button02").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button03").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button04").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button11").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button12").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button13").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Button14").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }
        // Test Discussion
        composeTestRule.onNodeWithTag("DropDownButton").performClick()
        composeTestRule.onNodeWithTag("CommentField").performTextInput("Good Question!")
        composeTestRule.onNodeWithTag("SendButton").performClick()
        composeTestRule.onNodeWithTag("Listen2Screen").performScrollToNode(hasText("Good Question!"))
        composeTestRule.onNodeWithTag("DropDownButton").performClick()

        val actions2 = listOf(
            {
                composeTestRule.onNodeWithTag("Button01").performClick()
                composeTestRule.onNodeWithTag("Button11").performClick()
                composeTestRule.onNodeWithTag("Button12").performClick()
                composeTestRule.onNodeWithTag("Button13").performClick()
                composeTestRule.onNodeWithTag("Button14").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Button02").performClick()
                composeTestRule.onNodeWithTag("Button11").performClick()
                composeTestRule.onNodeWithTag("Button12").performClick()
                composeTestRule.onNodeWithTag("Button13").performClick()
                composeTestRule.onNodeWithTag("Button14").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Button03").performClick()
                composeTestRule.onNodeWithTag("Button11").performClick()
                composeTestRule.onNodeWithTag("Button12").performClick()
                composeTestRule.onNodeWithTag("Button13").performClick()
                composeTestRule.onNodeWithTag("Button14").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            },
            {
                composeTestRule.onNodeWithTag("Button04").performClick()
                composeTestRule.onNodeWithTag("Button11").performClick()
                composeTestRule.onNodeWithTag("Button12").performClick()
                composeTestRule.onNodeWithTag("Button13").performClick()
                composeTestRule.onNodeWithTag("Button14").performClick()
                composeTestRule.onNodeWithText("CONTINUE").performClick()
            }
        )

        // Execute actions sequentially until Vocab2Screen no longer exists
        for (action in actions2) {
            action() // Run the action
            composeTestRule.waitForIdle() // Wait for the UI to stabilize

            try {
                composeTestRule.onNodeWithTag("Listen2Screen").assertDoesNotExist()
                break // Exit the loop if Vocab1Screen does not exist
            } catch (_: AssertionError) {
                // Continue to the next action if Vocab2Screen still exists
            }
        }

        // Wait until the CompleteExerciseScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("CompleteExerciseScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("ListeningTest", "CompleteExerciseScreen is not displayed yet")
                false
            }
        }
    }

}