package com.example.fieldwise.utils

import android.content.Context
//import io.mockk.every
//import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


// Minimal testing since AudioManager is a wrapper around Android's MediaRecorder
//class AudioManagerTest {
//private lateinit var context: Context
//   private lateinit var audioManager: AudioManager

//   @TempDir
//   lateinit var tempDir: File

//   @BeforeEach
//   fun setup() {
//      context = mockk {
//         every { externalCacheDir } returns tempDir
//      }
//      audioManager = AudioManager(context)
//   }

//   @Test
//   fun `startRecording returns null when external cache dir is not available`() {
      // Arrange
//      every { context.externalCacheDir } returns null

      // Act
//      val result = audioManager.startRecording()

      // Assert
//      assertNull(result)
//   }

//   @Test
//   fun `stopRecording does not throw when recorder is null`() {
//      // Act & Assert - should not throw
//      audioManager.stopRecording()
//   }
//}