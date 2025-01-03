package com.example.fieldwise.repository

import com.example.fieldwise.model.*
import com.example.fieldwise.network.ApiService
import com.example.fieldwise.network.KtorClient

class AiRepository {
    private val apiService = ApiService(KtorClient.client)

    suspend fun converse(request: ConverseRequest): ConverseResponse {
        return apiService.converse(request)
    }

    suspend fun transcribe(audio: ByteArray): TranscribeResponse {
        return apiService.transcribe(audio)
    }
}