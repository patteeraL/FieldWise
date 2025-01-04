package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class ConverseRequest(
    val language: String,
    val script: String,
    val history: List<Message>? = null
)