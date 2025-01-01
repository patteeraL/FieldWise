package com.example.fieldwise.ui.screen.lessons.speaking

import Discussion
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.MicButton
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

//back-end
data class SpeakingItemQuestion(val name: String, val text: String, val sound: String)
data class QuestionDataSpeaking(val status: Boolean, val question: List<SpeakingItemQuestion>, val comments: List<String>)

@Composable
fun getDataSpeaking(language: String, course: String, lesson: String, question: String):List<QuestionDataSpeaking> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val questionData = remember { mutableStateListOf<SpeakingItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot.child(language).child(course).child(lesson).child("Speaking").child(question)
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
            if (commentsData.isEmpty()){
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
        storageReference.getFile(destinationFile).addOnSuccessListener {
            Log.d("FirebaseStorage", "File downloaded: ${destinationFile.absolutePath}")
            fileUri.value = Uri.fromFile(destinationFile)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseStorage", "Error downloading file: ${exception.message}")
        }
    }
    return fileUri.value
}

@Composable
fun SpeakingScreen1(
    modifier: Modifier = Modifier,
    NextExercise: () -> Unit,
    ExitLesson: () -> Unit,
    type: String?
) {
    //FOR EACH SPEAKING EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val language = "English"
    val course = "CS"
    val lesson = "Basics of Program Development"
    val question = "Q1"
    val speakingData = getDataSpeaking(language, course, lesson, question)
    val questionText: String
    val audioUri: Uri?

    if (speakingData.isNotEmpty() && speakingData[0].status) {
        val question = speakingData[0].question
        Log.d("FirebaseCheck", "Question List: $question")
        audioUri = speakingMP3Storage(question[0].sound, "Speaking-Question-Sound")
        questionText = question[0].text //STORES THE QUESTION IN THIS VARIABLE
        val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize().background(Color(0xFF073748))
        .padding(start = 20.dp, end = 20.dp).verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            CloseButton(onClick = { ExitLesson() })
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = modifier
            .fillMaxSize()) {
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                TextToSpeechButton(onClick = {

                    Log.i("AudioStatus", "PRESSED $audioUri")
                    if (audioUri != null) {
                        try {
                            MediaPlayer().apply {
                                setDataSource(context, audioUri)
                                prepare()
                                start()
                            }
                        } catch (e: Exception) {
                            Log.e("AudioStatus", "Error playing audio: ${e.message}")
                        }
                    } else {
                        Log.w("AudioStatus", "Audio file not downloaded yet!")
                    }
                } )
                Spacer(modifier = Modifier.width(20.dp))
                Column ( modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp)).padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = questionText,
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(220.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){MicButton(onClick = {/* Open Mic */})}
            Spacer(modifier = Modifier.height(100.dp))
            MainButton(button = "CONTINUE", onClick = { NextExercise() }, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            val discussionComments: List<String>
            if (speakingData.isNotEmpty()) {
                val comment = speakingData[0].comments
                if (comment.isNotEmpty()) {
                    discussionComments = speakingData[0].comments
                    Discussion(comments = discussionComments)
                }
            }
        }
    }

    }
}

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

