package com.example.fieldwise.ui.screen.lessons.conversation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fieldwise.ui.theme.FieldWiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationScreenTest {

   @get:Rule
   val composeTestRule = createComposeRule()

   // Test only Conversation Screen
   @Test
   fun testInitialUiState() {
      composeTestRule.setContent {
         FieldWiseTheme {
            ConversationScreen1(
               ExitLesson = { {} },
               NextExercise = { {} },
               type = "exercise"
            )
         }
      }

      // Verify initial UI elements
      composeTestRule.onNodeWithText("Let's Chat with AI !").assertExists()
      composeTestRule.onNodeWithText("Type a message...").assertExists()
      composeTestRule.onNodeWithText("CONTINUE").assertExists()
      composeTestRule.onNodeWithText("Hi, are you ready for today's lesson?").assertExists()
   }

   @Test
   fun testMessageSending() {
      composeTestRule.setContent {
         FieldWiseTheme {
            ConversationScreen1(
               ExitLesson = { @Composable {} },
               NextExercise = { @Composable {} },
               type = "exercise"
            )
         }
      }

      // Type and send a message
      composeTestRule.onNode(hasSetTextAction()).performTextInput("Hello AI")
      composeTestRule.onNodeWithTag("SendButton").performClick()

      // Verify message appears in chat
      composeTestRule.onNodeWithText("Hello AI").assertExists()
   }
}