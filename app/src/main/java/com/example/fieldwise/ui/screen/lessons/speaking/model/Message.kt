package com.example.fieldwise.ui.screen.lessons.speaking.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val content: String
)