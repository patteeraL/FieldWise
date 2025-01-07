package com.example.fieldwise.ui.screen.lessons.vocabulary

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

class VocabularyTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testVocabulary() {
        // Set up the Compose content using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper(isFirstTime = false)
        }

        // Navigate to Vocabulary Screen
        composeTestRule.onNodeWithText("Lesson 2").performClick()
        composeTestRule.onNodeWithText("Resume").performClick()
        composeTestRule.onNodeWithText("Vocabulary").performClick()

        // Wait until the Vocab1 Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("Vocab1Screen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if images are displayed or not
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onNodeWithTag("Discussion").assertIsDisplayed()
                composeTestRule.onNodeWithTag("QuestionText").assertIsDisplayed()
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
        //composeTestRule.onNodeWithTag("Vocab1Sound").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SoundButton").performClick()

        // Test Discussion
        composeTestRule.onNodeWithTag("DropDownButton").performClick()
        composeTestRule.onNodeWithTag("CommentField").performTextInput("Good Question!")
        composeTestRule.onNodeWithTag("SendButton").performClick()
        composeTestRule.onNodeWithTag("Vocab1Screen").performScrollToNode(hasText("Good Question!"))
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

        // Execute actions sequentially until Vocab1Screen no longer exists
        for (action in actions1) {
            action() // Run the action
            composeTestRule.waitForIdle() // Wait for the UI to stabilize

            try {
                composeTestRule.onNodeWithTag("Vocab1Screen").assertDoesNotExist()
                break // Exit the loop if Vocab1Screen does not exist
            } catch (_: AssertionError) {
                // Continue to the next action if Vocab1Screen still exists
            }
        }

        // Wait until the Vocab2 Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("Vocab2Screen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if images are displayed or not
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onNodeWithTag("Discussion").assertIsDisplayed()
                composeTestRule.onNodeWithTag("QuestionText").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content1").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content2").assertIsDisplayed()
                composeTestRule.onNodeWithTag("Content3").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        // Check if text to speech button
        // Wait until the EnableNotifyScreen appears
        composeTestRule.onNodeWithTag("SoundButton").performClick()

        // Test Discussion
        composeTestRule.onNodeWithTag("DropDownButton").performClick()
        composeTestRule.onNodeWithTag("CommentField").performTextInput("Good Question!")
        composeTestRule.onNodeWithTag("SendButton").performClick()
        composeTestRule.onNodeWithTag("Vocab2Screen").performScrollToNode(hasText("Good Question!"))
        composeTestRule.onNodeWithTag("DropDownButton").performClick()

        val actions2 = listOf(
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
            }
        )

        // Execute actions sequentially until Vocab2Screen no longer exists
        for (action in actions2) {
            action() // Run the action
            composeTestRule.waitForIdle() // Wait for the UI to stabilize

            try {
                composeTestRule.onNodeWithTag("Vocab2Screen").assertDoesNotExist()
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
                Log.d("VocabularyTest", "CompleteExerciseScreen is not displayed yet")
                false
            }
        }


    }
}