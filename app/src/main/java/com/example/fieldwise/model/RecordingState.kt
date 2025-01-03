package com.example.fieldwise.model

sealed class RecordingState {
    object NotStarted : RecordingState()
    object Recording : RecordingState()
    object Completed : RecordingState()
    data class Error(val message: String) : RecordingState()
}

data class ProcessingResult(
    val isCorrect: Boolean,
    val transcript: String
)