package com.example.fieldwise.utils

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import java.io.File

// Audio Manager

class AudioManager(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null

    fun startRecording(): String? {
        try {
            val audioFile = File(context.externalCacheDir?.absolutePath + "/audio_record.mp4").apply {
                createNewFile()
            }

            mediaRecorder = MediaRecorder(context).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioChannels(1)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(audioFile.absolutePath)
                prepare()
                start()
            }

            return audioFile.absolutePath
        } catch (e: Exception) {
            Log.e("AudioManager", "Recording setup failed", e)
            return null
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                reset()
                release()
            }
            mediaRecorder = null
        } catch (e: Exception) {
            Log.e("AudioManager", "Failed to stop recording: ${e.message}")
        }
    }
}