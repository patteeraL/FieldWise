package com.example.fieldwise.network

import android.util.Log
import com.example.fieldwise.model.ConverseRequest
import com.example.fieldwise.model.ConverseResponse
import com.example.fieldwise.model.TranscribeResponse
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val BACKEND_URL = "https://backend-ai--fieldwise-26b6e.europe-west4.hosted.app"

class ApiService {
    private val client = KtorClient.client

    suspend fun converse(request: ConverseRequest): ConverseResponse {
        return try {
            Log.d("ApiService", "Sending converse request: $request")
            val response = client.post("$BACKEND_URL/ai/converse") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Log.d("ApiService", "Response status: ${response.status}")
            val converseResponse = response.body<ConverseResponse>()

            if (converseResponse.error != null) {
                Log.e("ApiService", "Conversation failed: ${converseResponse.error}")
            }

            converseResponse
        } catch (e: Exception) {
            Log.e("ApiService", "Conversation failed: ${e.message}")
            Log.e("ApiService", "Request: $request")
            Log.e("ApiService", "JsonRequest: ${Json.encodeToString(request)}")

            ConverseResponse(
                reply = e.message ?: "Conversation failed",
                feedback = "",
                correctnessPercent = 0
            )
        }
    }


    suspend fun transcribe(audio: ByteArray): TranscribeResponse {
        return try {
            Log.d("ApiService", "Audio data size: ${audio.size}")

            val response = client.submitFormWithBinaryData(
                url = "$BACKEND_URL/ai/transcribe",
                formData = formData {
                    append(
                        "audio",
                        audio,
                        Headers.build {
                            append(HttpHeaders.ContentType, "audio/mp4")
                            append(HttpHeaders.ContentDisposition, """filename="audio_record.mp4"""")
                        }
                    )
                }
            )
            Log.d("ApiService", "Response status: ${response.status}")
            val transcribeResponse = response.body<TranscribeResponse>()

            if (transcribeResponse.error != null) {
                Log.e("ApiService", "Transcription failed: ${transcribeResponse.error}")
            }

            transcribeResponse
        } catch (e: Exception) {
            Log.e("ApiService", "Transcription failed", e)
            TranscribeResponse(
                transcription = null,
                error = e.message
            )
        }
    }
}