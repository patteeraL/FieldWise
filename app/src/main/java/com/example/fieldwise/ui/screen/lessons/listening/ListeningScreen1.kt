package com.example.fieldwise.ui.screen.lessons.listening

import Discussion
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.fieldwise.ui.widget.ExerciseNotCompletePopUp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.random.Random

// back-end
data class Listen1ItemAnswer(val name: String, val correctAnswer: Boolean, val text: String)
data class Listen1ItemQuestion(val name: String, val sound: String)
data class QuestionAnswerDataListen1(val status: Boolean, val question: List<Listen1ItemQuestion>, val answer: List<Listen1ItemAnswer>, val comments: List<String>)

@Composable
fun getDataListen1(language: String, course: String, lesson: String, question: String):List<QuestionAnswerDataListen1> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val answerData = remember { mutableStateListOf<Listen1ItemAnswer>() }
    val questionData = remember { mutableStateListOf<Listen1ItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot.child(language).child(course).child(lesson).child("Listening").child("Type2").child(question)
            val questionSound = questionPath.child("Question").child("Sound").getValue(String::class.java) ?: ""
            questionData.add(Listen1ItemQuestion("Question", questionSound))

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

            questionPath.children.forEach { answerSnapshot ->
                if (answerSnapshot.key?.startsWith("Answer") == true) {
                    val name = answerSnapshot.key ?: ""
                    val correctAnswer = answerSnapshot.child("CorrectAnswer").getValue(Boolean::class.java) ?: false
                    val text = answerSnapshot.child("Text").getValue(String::class.java) ?: ""
                    answerData.add(Listen1ItemAnswer(name, correctAnswer, text))
                }
            }
            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }
    return if (dataStatus.value) {
        listOf(QuestionAnswerDataListen1(true, questionData, answerData, commentsData))
    } else {
        emptyList()
    }
}

@Composable
fun listen1MP3Storage(location: String, fileName: String): Uri? {
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
fun ListeningScreen1(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {
    //FOR EACH LISTENING1 EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val language = "English"
    val course = "CS"
    val lesson = "Basics of Program Development"
    val question = "Q1"
    val listenData = getDataListen1(language, course, lesson, question)

    val audioUri: Uri?
    val answerNumber = listOf(0, 1, 2, 3)
    val answerDisplay = listOf(
        mutableListOf<Listen1ItemAnswer>(),
        mutableListOf<Listen1ItemAnswer>(),
        mutableListOf<Listen1ItemAnswer>(),
        mutableListOf<Listen1ItemAnswer>(),
    )

    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    if (listenData.isNotEmpty() && listenData[0].status) {
        val question = listenData[0].question
        val answer = listenData[0].answer
        Log.d("FirebaseCheck", "Question List: $question, Answer List: $answer")
        audioUri = listen1MP3Storage(question[0].sound, "listen-Type2-Question-Sound")
        val answerOrder = remember { answerNumber.shuffled(Random) }
        for ((i, num) in answerOrder.withIndex()) {
            answerDisplay[num].add(answer[i])
        }

        val context = LocalContext.current
        var cardType1 by remember { mutableStateOf(CardType.WHITE) }
        var cardType2 by remember { mutableStateOf(CardType.WHITE) }
        var cardType3 by remember { mutableStateOf(CardType.WHITE) }
        var cardType4 by remember { mutableStateOf(CardType.WHITE) }
        var selectedCard by remember { mutableIntStateOf(0) }
        var answerResultStatus by remember { mutableStateOf(false) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF073748))
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CloseButton(onClick = { ExitLesson() })
                Spacer(modifier = Modifier.width(10.dp))
                LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = modifier) {
                Row(
                    modifier = modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = modifier
                            .height(80.dp)
                            .width(90.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = Color(0xFF0687A7),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                            )
                            Button(
                                onClick = {
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
                                }
                                ,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF1AB8E8
                                    )
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(15.dp)
                            ) {}
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(50.dp),
                                    painter = painterResource(id = R.drawable.texttospeechicon),
                                    contentDescription = "Icon"
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val title1 = answerDisplay[0][0].text
                    Card(
                        title = title1,
                        description = null,
                        cardType = cardType1,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[0][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.GREEN
                                cardType2 = CardType.WHITE
                                cardType3 = CardType.WHITE
                                cardType4 = CardType.WHITE
                                selectedCard = 1

                            } else{
                                cardType1 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                        4 -> cardType4 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = null,
                        imageUri = null
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    val title2 = answerDisplay[1][0].text
                    Card(
                        title = title2,
                        description = null,
                        cardType = cardType2,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[1][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.WHITE
                                cardType2 = CardType.GREEN
                                cardType3 = CardType.WHITE
                                cardType4 = CardType.WHITE
                                selectedCard = 2

                            } else{
                                cardType2 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                        4 -> cardType4 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = null,
                        imageUri = null
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val title3 = answerDisplay[2][0].text
                    Card(
                        title = title3,
                        description = null,
                        cardType = cardType3,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[2][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.WHITE
                                cardType2 = CardType.WHITE
                                cardType3 = CardType.GREEN
                                cardType4 = CardType.WHITE
                                selectedCard = 3

                            } else{
                                cardType3 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                        4 -> cardType4 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = null,
                        imageUri = null
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    val title4 = answerDisplay[3][0].text
                    Card(
                        title = title4,
                        description = null,
                        cardType = cardType4,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[3][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.WHITE
                                cardType2 = CardType.WHITE
                                cardType3 = CardType.WHITE
                                cardType4 = CardType.GREEN
                                selectedCard = 4

                            } else{
                                cardType4 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                        4 -> cardType4 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = null,
                        imageUri = null
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))
                MainButton(button = "CONTINUE", onClick = {
                    if (!answerResultStatus){ //IF ANSWER IS NOT CORRECT
                        dialogType = "INCORRECT_ANS"
                        showDialog = true
                    }
                    if (answerResultStatus){
                        NextExercise()}
                },
                    mainButtonType = MainButtonType.BLUE
                )
                if (showDialog) {
                    when (dialogType) { "INCORRECT_ANS" -> {
                        ExerciseNotCompletePopUp(
                            showDialog = showDialog,
                            onDismiss = { showDialog = false}
                        )
                    }}}
                Spacer(modifier = Modifier.height(50.dp))
                HorizontalDivider(thickness = 2.dp, color = Color.White)
                val discussionComments: List<String>
                if (listenData.isNotEmpty()) {
                    val comment = listenData[0].comments
                    if (comment.isNotEmpty()) {
                        discussionComments = listenData[0].comments
                        Discussion(comments = discussionComments)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListeningScreen1Preview() {
    FieldWiseTheme {
        ListeningScreen1(
            ExitLesson = {},
            NextExercise = {},
            type = ""
        )
    }
}
