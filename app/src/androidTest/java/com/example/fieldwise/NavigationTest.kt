package com.example.fieldwise

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.fieldwise.navigation.NavigationWrapper
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSplashToProfile() { //Navigation Test SplashScreen to ProfileScreen
        // Set up the compose using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper()
        }

        //Check if the SplashScreen is displayed initially
        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()

        //Click to go to loading screen
        composeTestRule.onNodeWithText("GET STARTED").performClick()

        //Check if the loadingScreen is displayed
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()

        //Wait until the LoadingScreen disappears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("LoadingScreen").assertDoesNotExist()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "LoadingScreen is still displayed")
                false
            }
        }
    //Check if the UsernameScreen is displayed
        composeTestRule.onNodeWithTag("UsernameScreen").assertIsDisplayed()

        //Enter text and proceed
        composeTestRule.onNodeWithTag("OutlinedTextField").performTextInput("FieldWiseADMIN")
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the SetDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("SetDailyGoalScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "SetDailyGoalScreen is not displayed yet")
                false
            }
        }

        //Select Daily Goal
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        //Wait until the EnableNotifyScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("EnableNotifyScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "EnableNotifyScreen is not displayed yet")
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
                Log.d("NavigationTest", "CourseManageScreen is not displayed yet")
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
                Log.d("NavigationTest", "CompleteScreen is not displayed yet")
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
                Log.d("NavigationTest", "HomeScreen is not displayed yet")
                false
            }
        }

        composeTestRule.onNodeWithTag("ProfileIconButton").performClick()

        // Wait until the Profile appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("ProfileScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "ProfileScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("AddFriendButton").performClick()
        // Wait until the AddFriend Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("AddFriendScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "AddFriendScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("SearchFriendField").performTextInput("FieldWise")
        composeTestRule.onNodeWithTag("GoBackButton").performClick()
        // Wait until the Profile appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("ProfileScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "ProfileScreen is not displayed yet")
                false
            }
        }

        composeTestRule.onNodeWithTag("SettingButton").performClick()
        // Wait until the Setting Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("SettingScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "SettingScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("DropDownMenu").performClick()
        composeTestRule.onNodeWithText("15 min / day (Serious)").performClick()
        composeTestRule.onNodeWithText("SAVE CHANGES").performClick()
        composeTestRule.waitForIdle() // Wait for the UI to stabilize

        try {
            composeTestRule.onNodeWithTag("SettingScreen").assertDoesNotExist()
        } catch (_: AssertionError) {
            // Continue to the next action if Listen1Screen still exists
        }
        composeTestRule.onNodeWithText("Continue").performClick()
    }

    @Test
    fun testSplashToLeaderboard() { //Navigation Test SplashScreen to ProfileScreen
        // Set up the compose using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper()
        }

        //Check if the SplashScreen is displayed initially
        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()

        //Click to go to loading screen
        composeTestRule.onNodeWithText("GET STARTED").performClick()

        //Check if the loadingScreen is displayed
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()

        //Wait until the LoadingScreen disappears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("LoadingScreen").assertDoesNotExist()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "LoadingScreen is still displayed")
                false
            }
        }
        //Check if the UsernameScreen is displayed
        composeTestRule.onNodeWithTag("UsernameScreen").assertIsDisplayed()

        //Enter text and proceed
        composeTestRule.onNodeWithTag("OutlinedTextField").performTextInput("FieldWiseADMIN")
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the SetDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("SetDailyGoalScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "SetDailyGoalScreen is not displayed yet")
                false
            }
        }

        //Select Daily Goal
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        //Wait until the EnableNotifyScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("EnableNotifyScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "EnableNotifyScreen is not displayed yet")
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
                Log.d("NavigationTest", "CourseManageScreen is not displayed yet")
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
                Log.d("NavigationTest", "CompleteScreen is not displayed yet")
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
                Log.d("NavigationTest", "HomeScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("LeaderBoardButton").performClick()

        // Wait until the LeaderBoard Screen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("LeaderBoardScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "LeaderBoardScreen is not displayed yet")
                false
            }
        }
    }

    @Test
    fun testSplashToCourseManage() { //Navigation Test SplashScreen to ProfileScreen
        // Set up the compose using NavigationWrapper's structure
        composeTestRule.setContent {
            NavigationWrapper()
        }

        //Check if the SplashScreen is displayed initially
        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()

        //Click to go to loading screen
        composeTestRule.onNodeWithText("GET STARTED").performClick()

        //Check if the loadingScreen is displayed
        composeTestRule.onNodeWithTag("LoadingScreen").assertIsDisplayed()

        //Wait until the LoadingScreen disappears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("LoadingScreen").assertDoesNotExist()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "LoadingScreen is still displayed")
                false
            }
        }
        //Check if the UsernameScreen is displayed
        composeTestRule.onNodeWithTag("UsernameScreen").assertIsDisplayed()

        //Enter text and proceed
        composeTestRule.onNodeWithTag("OutlinedTextField").performTextInput("FieldWiseADMIN")
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the SetDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("SetDailyGoalScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "SetDailyGoalScreen is not displayed yet")
                false
            }
        }

        //Select Daily Goal
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        //Wait until the EnableNotifyScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("EnableNotifyScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "EnableNotifyScreen is not displayed yet")
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
                Log.d("NavigationTest", "CourseManageScreen is not displayed yet")
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
                Log.d("NavigationTest", "CompleteScreen is not displayed yet")
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
                Log.d("NavigationTest", "HomeScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithTag("CourseManageButton").performClick()
        composeTestRule.onNodeWithText("Geography").performClick()
        composeTestRule.onNodeWithText("Thai").performClick()
        composeTestRule.onNodeWithTag("AddField").performClick()
        composeTestRule.waitForIdle() // Wait for the UI to stabilize

        try {
            composeTestRule.onNodeWithTag("HomeScreen").assertDoesNotExist()
        } catch (_: AssertionError) {
            // Continue to the next action if Listen1Screen still exists
        }

        // Wait until the AddDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("AddDailyGoalScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "AddDailyGoalScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the AddFieldScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("AddFieldScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "AddFieldScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithText("Geography").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the CompleteScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("CompleteScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "CompleteScreen is not displayed yet")
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
                Log.d("NavigationTest", "HomeScreen is not displayed yet")
                false
            }
        }

        composeTestRule.onNodeWithTag("CourseManageButton").performClick()
        composeTestRule.onNodeWithTag("AddLanguage").performClick()
        composeTestRule.waitForIdle() // Wait for the UI to stabilize

        try {
            composeTestRule.onNodeWithTag("HomeScreen").assertDoesNotExist()
        } catch (_: AssertionError) {
            // Continue to the next action if Listen1Screen still exists
        }

        // Wait until the AddDailyGoalScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("AddDailyGoalScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "AddDailyGoalScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithText("5 min / day").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the AddFieldScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("AddLanguageScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "AddLanguageScreen is not displayed yet")
                false
            }
        }
        composeTestRule.onNodeWithText("Thai").performClick()
        composeTestRule.onNodeWithText("Geography").performClick()
        composeTestRule.onNodeWithText("CONTINUE").performClick()

        // Wait until the CompleteScreen appears
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("CompleteScreen").assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                Log.d("NavigationTest", "CompleteScreen is not displayed yet")
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
                Log.d("NavigationTest", "HomeScreen is not displayed yet")
                false
            }
        }




    }







}

