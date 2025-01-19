package com.example.fieldwise.ui.screen.lessons.speaking.model

import kotlinx.serialization.Serializable

@Serializable
data class ConverseResponse(
    val reply: String,
    val feedback: String,
    val correctnessPercent: Int,
    val error: String? = null
)