package com.example.fieldwise

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.example.fieldwise.navigation.NavigationWrapper
import org.junit.Rule
import org.junit.Test

class ExerciseContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testVocab() {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
                Log.d("SplashScreenTest", "HomeScreen is not displayed yet")
                false
            }
        }
        // Navigate to Vocabulary Screen
        composeTestRule.onNodeWithText("Lesson 1").performClick()
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
                Log.d("SplashScreenTest", "CompleteExerciseScreen is not displayed yet")
                false
            }
        }


    }

    @Test
    fun testListening() {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
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
            } catch (_: AssertionError) {
                Log.d("SplashScreenTest", "HomeScreen is not displayed yet")
                false
            }
        }
        // Navigate to Listening Screen
        composeTestRule.onNodeWithText("Lesson 1").performClick()
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
                Log.d("SplashScreenTest", "CompleteExerciseScreen is not displayed yet")
                false
            }
        }
    }
}