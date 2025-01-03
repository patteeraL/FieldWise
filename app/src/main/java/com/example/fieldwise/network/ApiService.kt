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
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

private const val BACKEND_URL = "https://backend-ai--fieldwise-26b6e.europe-west4.hosted.app"

class ApiService {
    private val client = KtorClient.client

    suspend fun converse(request: ConverseRequest): ConverseResponse {
        return try { client.post("$BACKEND_URL/ai/converse") {
            setBody(request)
        }.body()
        } catch (e: Exception) {
            Log.e("ApiService", "Conversation failed: ${e.message}")
            ConverseResponse(
                reply = "Sorry, I couldn't understand that.",
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