package com.example.fieldwise.ui.screen.lessons.speaking.model

import kotlinx.serialization.Serializable

@Serializable
data class TranscribeResponse(
    val transcription: String = "",
    val error: String? = null,
)