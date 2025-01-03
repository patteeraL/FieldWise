package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class TranscribeResponse(
    val transcription: String? = null,
    val error: String? = null,
    val detailedError: String? = null
)