package com.example.fieldwise.network

import android.util.Log
import com.example.fieldwise.model.ConverseRequest
import com.example.fieldwise.model.ConverseResponse
import com.example.fieldwise.model.TranscribeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

private const val BACKEND_URL = "https://backend-ai--fieldwise-26b6e.europe-west4.hosted.app"

open class ApiService(protected open val client: HttpClient = KtorClient.client) {
    suspend fun converse(request: ConverseRequest): ConverseResponse {
        return try {
            Log.d("ApiService", "Sending converse request: $request")
            val response = client.post("$BACKEND_URL/ai/converse") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.body()
        } catch (e: Exception) {
            Log.e("ApiService", "Conversation failed: ${e.message}")
            ConverseResponse(
                reply = e.message ?: "Conversation failed",
                feedback = "",
                correctnessPercent = 0
            )
        }
    }

    suspend fun transcribe(audio: ByteArray, fileType: String = "mp4"): TranscribeResponse {
        val fromTypeToContentType = mapOf(
            "mp3" to "mpeg",
            "mp4" to "mp4",
            "wav" to "wav",
            "ogg" to "ogg"
        )
        return try {
            Log.d("ApiService", "Audio data size: ${audio.size}")

            val response = client.submitFormWithBinaryData(
                url = "$BACKEND_URL/ai/transcribe",
                formData = formData {
                    append(
                        "audio",
                        audio,
                        Headers.build {
                            append(HttpHeaders.ContentType, "audio/${fromTypeToContentType[fileType]}")
                            append(HttpHeaders.ContentDisposition, """filename="audio_record.$fileType"""")
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
                error = e.message
            )
        }
    }
}