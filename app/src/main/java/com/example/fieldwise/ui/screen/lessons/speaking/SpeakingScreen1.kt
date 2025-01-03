package com.example.fieldwise.ui.screen.lessons.speaking

import Discussion
import android.Manifest
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.fieldwise.model.ProcessingResult
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.ExerciseNotCompletePopUp
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.MicButton
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton
import com.example.fieldwise.utils.AudioManager
import com.example.fieldwise.viewmodel.AiViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

// -------------------------------------------------
// DATA MODELS AND UTILS
// -------------------------------------------------

data class SpeakingItemQuestion(
    val name: String,
    val text: String,
    val sound: String
)

data class QuestionDataSpeaking(
    val status: Boolean,
    val question: List<SpeakingItemQuestion>,
    val comments: List<String>
)

fun isTranscriptionEqualsToQuestion(transcription: String, question: String): Boolean {
    val cleanedTranscription = transcription
        .lowercase()
        .trim()
        .replace(Regex("[^A-Za-z0-9 ]"), "")

    val cleanedQuestion = question
        .lowercase()
        .replace("repeat: ", "")
        .replace(Regex("[^A-Za-z0-9 ]"), "")
        .trim()

    Log.d(
        "SpeakingScreen",
        "Comparing \n$cleanedQuestion \nwith \n$cleanedTranscription, " +
                "\nresult: ${cleanedTranscription == cleanedQuestion}"
    )
    return cleanedTranscription == cleanedQuestion
}

@Composable
fun getDataSpeaking(
    language: String,
    course: String,
    lesson: String,
    question: String
): List<QuestionDataSpeaking> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val questionData = remember { mutableStateListOf<SpeakingItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot
                .child(language)
                .child(course)
                .child(lesson)
                .child("Speaking")
                .child(question)

            val questionText = questionPath.child("Text").getValue(String::class.java) ?: ""
            val questionSound = questionPath.child("Sound").getValue(String::class.java) ?: ""
            questionData.add(SpeakingItemQuestion("Question", questionText, questionSound))

            val commentsSnapshot = questionPath.child("Comments")
            commentsSnapshot.children.forEach { commentSnapshot ->
                val commentText = commentSnapshot.getValue(String::class.java)
                if (!commentText.isNullOrEmpty()) {
                    commentsData.add(commentText)
                }
            }
            if (commentsData.isEmpty()) {
                commentsData.add("Loading...")
            }
            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }

    return if (dataStatus.value) {
        listOf(QuestionDataSpeaking(true, questionData, commentsData))
    } else {
        emptyList()
    }
}

@Composable
fun speakingMP3Storage(location: String, fileName: String): Uri? {
    val context = LocalContext.current
    val storageReference = Firebase.storage.getReferenceFromUrl(location)
    val fieldWiseDir = File(context.filesDir, "FieldWise")
    if (!fieldWiseDir.exists()) fieldWiseDir.mkdirs()

    val destinationFile = File(fieldWiseDir, fileName)
    val fileUri = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        Log.d("LaunchedEffect", "Starting download")
        storageReference.getFile(destinationFile)
            .addOnSuccessListener {
                Log.d("FirebaseStorage", "File downloaded: ${destinationFile.absolutePath}")
                fileUri.value = Uri.fromFile(destinationFile)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Error downloading file: ${exception.message}")
            }
    }
    return fileUri.value
}

// -------------------------------------------------
// COMPOSABLES
// -------------------------------------------------

@Composable
fun SpeakingScreen1(
    modifier: Modifier = Modifier,
    NextExercise: () -> Unit,
    ExitLesson: () -> Unit,
    type: String?
) {
    val context = LocalContext.current
    val viewModel = AiViewModel()
    val audioManager = remember { AudioManager(context) }

    // States
    val recordingState = remember { mutableStateOf<RecordingState>(RecordingState.NotStarted) }
    val audioFilePath = remember { mutableStateOf<String?>(null) }
    val questionText = remember { mutableStateOf("") }
    val processingResult = remember { mutableStateOf<ProcessingResult?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogType = remember { mutableStateOf("") }
    val isBeingTranscribed = remember { mutableStateOf(false) }

    // Permission
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Log.d("SpeakingScreen", "Recording permission denied")
            dialogType.value = "PERMISSION_DENIED"
            showDialog.value = true
        }
    }

    // Request recording permission
    LaunchedEffect(Unit) {
        Log.d("SpeakingScreen", "Requesting recording permission")
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    // Data from Firebase
    val language = "English"
    val course = "CS"
    val lesson = "Basics of Program Development"
    val question = "Q1"
    val speakingData = getDataSpeaking(language, course, lesson, question)

    // If we have data
    if (speakingData.isNotEmpty() && speakingData[0].status) {
        val questionList = speakingData[0].question
        questionText.value = questionList[0].text
        val audioUri = speakingMP3Storage(questionList[0].sound, "Speaking-Question-Sound")

        // Main layout
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF073748))
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Top bar: close + progress
            TopBar(
                onCloseClick = { ExitLesson() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Body content
            BodyContent(
                questionText = questionText.value,
                audioUri = audioUri,
                startPlayingAudio = { uri ->
                    uri?.let {
                        try {
                            MediaPlayer().apply {
                                setDataSource(context, it)
                                prepare()
                                start()
                            }
                        } catch (e: Exception) {
                            Log.e("SpeakingScreen", "Error playing audio: ${e.message}")
                        }
                    }
                },
                recordingState = recordingState.value,
                onRecordingStart = {
                    Log.d("SpeakingScreen", "Starting new recording")
                    processingResult.value = null
                    audioFilePath.value = audioManager.startRecording()
                    recordingState.value = if (audioFilePath.value != null) {
                        RecordingState.Recording
                    } else {
                        Log.e("SpeakingScreen", "Failed to start recording")
                        RecordingState.Error("Failed to start recording")
                    }
                },
                onRecordingStop = {
                    Log.d("SpeakingScreen", "Stopping recording")
                    audioManager.stopRecording()
                    recordingState.value = RecordingState.Completed
                    isBeingTranscribed.value = true

                    // Process the audio
                    audioFilePath.value?.let { filePath ->
                        val audioBytes = File(filePath).readBytes()
                        viewModel.transcribe(audioBytes).observe(context as LifecycleOwner) { response ->
                            if (response.error != null) {
                                dialogType.value = "TRANSCRIPTION_ERROR"
                                showDialog.value = true
                                isBeingTranscribed.value = false
                                return@observe
                            }
                            val isCorrect = isTranscriptionEqualsToQuestion(
                                response.transcription ?: "",
                                questionText.value
                            )
                            processingResult.value = ProcessingResult(
                                isCorrect = isCorrect,
                                transcript = response.transcription ?: ""
                            )
                            isBeingTranscribed.value = false
                        }
                    }
                },
                transcriptionText = processingResult.value?.transcript,
                isProcessing = isBeingTranscribed.value
            )

            // “Continue” Button + Pop-up Handling
            BottomControls(
                onContinueClick = {
                    when {
                        recordingState.value !is RecordingState.Completed -> {
                            dialogType.value = "NO_RECORDING"
                            showDialog.value = true
                        }
                        processingResult.value?.isCorrect == false -> {
                            dialogType.value = "INCORRECT_ANS"
                            showDialog.value = true
                        }
                        processingResult.value?.isCorrect == true -> {
                            NextExercise()
                        }
                    }
                },
                showDialog = showDialog.value,
                onDismissDialog = { showDialog.value = false }
            )

            // Comments Section
            if (speakingData[0].comments.isNotEmpty()) {
                Discussion(comments = speakingData[0].comments)
            }
        }
    }
}

// -------------------------------------------------
// SUBCOMPOSABLES
// -------------------------------------------------

@Composable
fun TopBar(onCloseClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CloseButton(onClick = onCloseClick)
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
    }
}

@Composable
fun BodyContent(
    questionText: String,
    audioUri: Uri?,
    startPlayingAudio: (Uri?) -> Unit,
    recordingState: RecordingState,
    onRecordingStart: () -> Unit,
    onRecordingStop: () -> Unit,
    transcriptionText: String?,
    isProcessing: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Question area
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextToSpeechButton(onClick = { startPlayingAudio(audioUri) })
            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = questionText,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(220.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Spacer(modifier = Modifier.height(50.dp))

        // Recording controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            when (recordingState) {
                is RecordingState.Recording -> {
                    MicButton(
                        isRecording = true,
                        onClick = onRecordingStop
                    )
                }
                else -> {
                    MicButton(onClick = onRecordingStart)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Processing text
        if (isProcessing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
            Text(
                text = "Processing your answer...",
                color = Color.White,
                textAlign = TextAlign.Center
            )}
        }

        // Transcription
        transcriptionText?.let { text ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
            Text(
                text = "Your answer: $text",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )}
        }
    }
}

@Composable
fun BottomControls(
    onContinueClick: () -> Unit,
    showDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    MainButton(
        button = "CONTINUE",
        onClick = onContinueClick,
        mainButtonType = MainButtonType.BLUE
    )

    if (showDialog) {
        ExerciseNotCompletePopUp(
            showDialog = true,
            onDismiss = onDismissDialog
        )
    }

    Spacer(modifier = Modifier.height(50.dp))
    HorizontalDivider(thickness = 2.dp, color = Color.White)
}

// -------------------------------------------------
// PREVIEW
// -------------------------------------------------

@Preview(showBackground = true)
@Composable
fun SpeakingScreen1Preview() {
    FieldWiseTheme {
        SpeakingScreen1(
            NextExercise = {},
            ExitLesson = {},
            type = ""
        )
    }
}
