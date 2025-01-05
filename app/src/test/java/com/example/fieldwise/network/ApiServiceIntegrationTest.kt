package com.example.fieldwise.viewmodel

import android.util.Log
import com.example.fieldwise.model.ConverseRequest
import com.example.fieldwise.model.Message
import com.example.fieldwise.network.ApiService
import com.example.fieldwise.network.KtorClient
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiServiceIntegrationTest {
   private lateinit var apiService: ApiService

   @BeforeEach
   fun setup() {
      // Mock Android Log calls
      mockkStatic(Log::class)
      every { Log.e(any(), any()) } returns 0
      every { Log.d(any(), any()) } returns 0

      apiService = ApiService(KtorClient.client) // Real client
   }

   @Test
   fun `should handle real conversation flow`() = runTest {
      // Arrange
      val history = listOf(
         Message("user", "What are relational databases?")
      )
      val request = ConverseRequest(
         language = "English",
         script = """
                Name: Relation Databases
                Topics:
                1. What are relational databases?
                2. How do they work?
                3. What are the benefits?
            """.trimIndent(),
         history = history
      )

      // Act
      val response = apiService.converse(request)

      // Assert
      assertNotNull(response)
      assertTrue(response.reply.isNotEmpty())
      assertTrue(response.correctnessPercent in 0..100)
   }

   @Test
   fun `should handle conversation end token`() = runTest {
      val history = listOf(
         Message("user", "Goodbye"),
         Message("assistant", "Would you like to end the conversation?"),
         Message("user", "Yes")
      )
      val request = ConverseRequest(
         language = "English",
         script = "Test script",
         history = history
      )

      val response = apiService.converse(request)

      assertTrue(response.reply.uppercase().contains("@END_CONVERSATION"))
   }

   @Test
   fun `should provide feedback for incorrect answers`() = runTest {
      val history = listOf(
         Message("user", "A relational database is a programming language")
      )
      val request = ConverseRequest(
         language = "English",
         script = "Name: Relation Databases...",
         history = history
      )

      val response = apiService.converse(request)

      assertTrue(response.correctnessPercent < 100)
      assertTrue(response.feedback.isNotEmpty())
      assertTrue(!response.feedback.uppercase().contains("@NO_FEEDBACK"))
   }

   @Test
   fun `should handle successful transcription`() = runTest {
      // Arrange
      val audioBytes = requireNotNull(javaClass.getResource("/test_audio.mp4")?.readBytes()) {
         "Test audio file not found"
      }

      // Act
      val response = apiService.transcribe(audioBytes)

      // Assert
      assertNotNull(response)
      assertTrue(response.error.isNullOrBlank())
   }
}