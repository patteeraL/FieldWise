package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class TranscribeResponse(
    val transcription: String = "",
    val error: String? = null,
)