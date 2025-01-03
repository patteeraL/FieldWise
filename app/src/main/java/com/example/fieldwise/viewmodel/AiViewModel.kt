package com.example.fieldwise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fieldwise.model.*
import com.example.fieldwise.repository.AiRepository
import kotlinx.coroutines.Dispatchers

// ViewModel class for the AI feature:
// - Uses the AiRepository class to interact with the API
// - Provides live data for the UI to observe
// - Uses the Dispatchers.IO coroutine context for network operations
class AiViewModel : ViewModel() {

    private val repository = AiRepository()

    fun converse(request: ConverseRequest) = liveData(Dispatchers.IO) {
        val response = repository.converse(request)
        emit(response)
    }

    fun transcribe(audio: ByteArray) = liveData(Dispatchers.IO) {
        val response = repository.transcribe(audio)
        emit(response)
    }
}