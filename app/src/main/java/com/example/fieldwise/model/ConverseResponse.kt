package com.example.fieldwise.model

import kotlinx.serialization.Serializable

@Serializable
data class ConverseResponse(
    val reply: String,
    val feedback: String,
    val correctnessPercent: Int
)