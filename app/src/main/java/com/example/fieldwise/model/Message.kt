package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val content: String
)