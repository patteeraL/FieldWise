package com.example.fieldwise.ui.screen.lessons.speaking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fieldwise.ui.screen.lessons.speaking.model.ConverseRequest
import com.example.fieldwise.network.ApiService
import kotlinx.coroutines.Dispatchers

class AiViewModel(private val service: ApiService = ApiService()) : ViewModel() {
    fun converse(request: ConverseRequest) = liveData(Dispatchers.IO) {
        emit(service.converse(request))
    }

    fun transcribe(audio: ByteArray) = liveData(Dispatchers.IO) {
        emit(service.transcribe(audio))
    }
}