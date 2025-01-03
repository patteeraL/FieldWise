package com.example.fieldwise.ui.screen.lessons.speaking

sealed class RecordingState {
   object NotStarted : RecordingState()
   object Recording : RecordingState()
   object Completed : RecordingState()
   data class Error(val message: String) : RecordingState()
}
