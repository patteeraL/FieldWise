package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class ConverseRequest(
    val message: String,
    val language: String,
    val script: String,
    val history: List<Message>? = null
)