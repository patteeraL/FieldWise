package com.example.fieldwise.ui.screen.lessons.vocabulary

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton
import kotlin.random.Random
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.ktx.storage
import java.io.File


// back-end
data class Vocab1ItemAnswer(val name: String, val correctAnswer: Boolean, val text: String, val pic: String)
data class Vocab1ItemQuestion(val name: String, val text: String, val sound: String)
data class QuestionAnswerData(val status: Boolean, val question: List<Vocab1ItemQuestion>, val answer: List<Vocab1ItemAnswer>)
data class UriInfo(val Uri: Uri, val name: String, val status: Boolean)

@Composable
fun getDataVocab1(Language: String, Course: String, Lesson: String, Question: String):List<QuestionAnswerData> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val Answerdata = remember { mutableStateListOf<Vocab1ItemAnswer>() }
    val Questiondata = remember { mutableStateListOf<Vocab1ItemQuestion>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath =
                dataSnapshot.child(Language).child(Course).child(Lesson).child("Vocabulary")
                    .child("Type2").child(Question)
            val questionText =
                questionPath.child("Question").child("Text").getValue(String::class.java) ?: ""
            val questionSound =
                questionPath.child("Question").child("Sound").getValue(String::class.java) ?: ""
            Questiondata.add(Vocab1ItemQuestion("Question", questionText, questionSound))

            questionPath.children.forEach { answerSnapshot ->
                if (answerSnapshot.key?.startsWith("Answer") == true) {
                    val name = answerSnapshot.key ?: ""
                    val correctAnswer = answerSnapshot.child("CorrectAnswer").getValue(Boolean::class.java) ?: false
                    val text = answerSnapshot.child("Text").getValue(String::class.java) ?: ""
                    val pic = answerSnapshot.child("Pic").getValue(String::class.java) ?: ""
                    Answerdata.add(Vocab1ItemAnswer(name, correctAnswer, text, pic))
                }
            }
            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }
    if (dataStatus.value) {
        return listOf(QuestionAnswerData(true, Questiondata, Answerdata))
    } else {
        return emptyList()
    }
}

@Composable
fun Vocab1PicStorage(Location: String, fileName: String): Uri? {
    val context = LocalContext.current
    val storageReference = Firebase.storage.getReferenceFromUrl("$Location")
    val fieldWiseDir = File(context.filesDir, "FieldWise")
    if (!fieldWiseDir.exists()) fieldWiseDir.mkdirs()
    val destinationFile = File(fieldWiseDir, "$fileName")
    val fileUri = remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(Unit) {
        storageReference.getFile(destinationFile).addOnSuccessListener {
            fileUri.value = Uri.fromFile(destinationFile)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseStorage", "Error downloading file: ${exception.message}")
        }
    }
    return fileUri.value
}

@Composable
fun Vocab1MP3Storage(Location: String, fileName: String): Uri? {
    val context = LocalContext.current
    val storageReference = Firebase.storage.getReferenceFromUrl("$Location")
    val fieldWiseDir = File(context.filesDir, "FieldWise")
    if (!fieldWiseDir.exists()) fieldWiseDir.mkdirs()
    val destinationFile = File(fieldWiseDir, "$fileName")
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
fun VocabScreen1(modifier: Modifier = Modifier, ExitLesson: () -> Unit, NextExercise: () -> Unit) {
    //FOR EACH VOCAB EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val Language = "English"
    val Course = "CS"
    val Lesson = "Basics of Program Development"
    val Question = "Q1"
    val vocabData = getDataVocab1(Language, Course, Lesson, Question)

    var questionText = "Loading..."
    var audioUri: Uri? = null
    val answerNumber = listOf(0, 1, 2, 3)
    val answerDisplay = listOf(
        mutableListOf<Vocab1ItemAnswer>(),
        mutableListOf<Vocab1ItemAnswer>(),
        mutableListOf<Vocab1ItemAnswer>(),
        mutableListOf<Vocab1ItemAnswer>()
    )
    val imageUriList = listOf(
        mutableListOf<UriInfo?>(null),
        mutableListOf<UriInfo?>(null),
        mutableListOf<UriInfo?>(null),
        mutableListOf<UriInfo?>(null)
    )

    if (vocabData.isNotEmpty() && vocabData[0].status) {
        val question = vocabData[0].question
        val answer = vocabData[0].answer
        Log.d("FirebaseCheck", "Question List: $question, Answer List: $answer")
        audioUri = Vocab1MP3Storage("${question[0].sound}", "Vocab-Type2-Question-Sound")
        questionText = question[0].text
        val answerOrder = remember { answerNumber.shuffled(Random) }
        var i = 0
        for (num in answerOrder) {
            answerDisplay[num].add(answer[i])
            i++
        }
        val UriTemp1 = Uri.parse("${Vocab1PicStorage("${answerDisplay[0][0].pic}", "Vocab-Type2-Answer1")}") // Create Uri from pic string
        imageUriList[0][0] = UriInfo(UriTemp1, answerDisplay[0][0].text, answerDisplay[0][0].correctAnswer)
        val UriTemp2 = Uri.parse("${Vocab1PicStorage("${answerDisplay[1][0].pic}","Vocab-Type2-Answer2")}")
        imageUriList[1][0] = UriInfo(UriTemp2, answerDisplay[1][0].text, answerDisplay[1][0].correctAnswer)
        val UriTemp3 = Uri.parse("${Vocab1PicStorage("${answerDisplay[2][0].pic}","Vocab-Type2-Answer3")}")
        imageUriList[2][0] = UriInfo(UriTemp3, answerDisplay[2][0].text, answerDisplay[2][0].correctAnswer)
        val UriTemp4 = Uri.parse("${Vocab1PicStorage("${answerDisplay[3][0].pic}","Vocab-Type2-Answer4")}")
        imageUriList[3][0] = UriInfo(UriTemp4, answerDisplay[3][0].text, answerDisplay[3][0].correctAnswer)
    } else {
        Log.d("FirebaseCheck", "List Extraction Failed!")
    }
    val context = LocalContext.current

    var AnswerResultStatus = false
    Column(modifier = modifier.fillMaxSize().background(Color(0xFF073748))
        .padding(start = 20.dp, end = 20.dp)) {
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
                            val mediaPlayer = MediaPlayer().apply {
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
                    Text(text = "${questionText}",
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                var imageUri1 = imageUriList[0][0]?.Uri ?: null
                var title1 = imageUriList[0][0]?.name ?: "Loading..."
                Card(
                    title = title1,
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_SQUARE,
                    progress = null,
                    complete = null,
                    onClick = { AnswerResultStatus = imageUriList[0][0]?.status ?: false },
                    imageResId = null,
                    imageUri = imageUri1
                )
                Spacer(modifier = Modifier.width(20.dp))
                var imageUri2 = imageUriList[1][0]?.Uri ?: null
                var title2 = imageUriList[1][0]?.name ?: "Loading..."
                Card(
                    title = title2,
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_SQUARE,
                    progress = null,
                    complete = null,
                    onClick = { AnswerResultStatus = imageUriList[1][0]?.status ?: false  },
                    imageResId = null,
                    imageUri = imageUri2
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                var imageUri3 = imageUriList[2][0]?.Uri ?: null
                var title3 = imageUriList[2][0]?.name ?: "Loading..."
                Card(
                    title = title3,
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_SQUARE,
                    progress = null,
                    complete = null,
                    onClick = { AnswerResultStatus = imageUriList[2][0]?.status ?: false  },
                    imageResId = null,
                    imageUri = imageUri3
                )
                Spacer(modifier = Modifier.width(20.dp))
                var imageUri4 = imageUriList[3][0]?.Uri ?: null
                var title4 = imageUriList[3][0]?.name ?: "Loading..."
                Card(
                    title = title4,
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_SQUARE,
                    progress = null,
                    complete = null,
                    onClick = { AnswerResultStatus = imageUriList[3][0]?.status ?: false  },
                    imageResId = null,
                    imageUri = imageUri4
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            MainButton(button = "CONTINUE", onClick = {
                //ON CLICK, CHECK IF THE CHOSEN ANSWER IS CORRECT OR NOT BY CHECKING AnswerResultStatus = false or true AND CHANGE ANSWER COLOUR
                NextExercise() }, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun VocabScreen1Preview() {
    FieldWiseTheme {
        VocabScreen1(
            NextExercise = {},
            ExitLesson = {}
        )
    }
}

