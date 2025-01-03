package com.example.fieldwise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fieldwise.model.ConverseRequest
import com.example.fieldwise.network.ApiService
import kotlinx.coroutines.Dispatchers

class AiViewModel : ViewModel() {

    private val service = ApiService()

    fun converse(request: ConverseRequest) = liveData(Dispatchers.IO) {
        val response = service.converse(request)
        emit(response)
    }

    fun transcribe(audio: ByteArray) = liveData(Dispatchers.IO) {
        val response = service.transcribe(audio)
        emit(response)
    }
}