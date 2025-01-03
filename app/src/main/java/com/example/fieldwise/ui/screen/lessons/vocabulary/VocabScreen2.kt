package com.example.fieldwise.ui.screen.lessons.vocabulary

import Discussion
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.screen.lessons.listening.commentNumberListen2
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.DeleteCoursePopUp
import com.example.fieldwise.ui.widget.ExerciseNotCompletePopUp
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.random.Random

// back-end
data class Vocab2ItemAnswer(val name: String, val correctAnswer: Boolean, val text: String)
data class Vocab2ItemQuestion(val name: String, val text: String, val sound: String)
data class QuestionAnswerDataVocab2(val status: Boolean, val question: List<Vocab2ItemQuestion>, val answer: List<Vocab2ItemAnswer>, val comments: List<String>)
var commentNumberVocab2 = 0
var newCommentsDataVocab2: List<String> = emptyList()

@Composable
fun getDataVocab2(language: String, course: String, lesson: String, question: String):List<QuestionAnswerDataVocab2> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val answerData = remember { mutableStateListOf<Vocab2ItemAnswer>() }
    val questionData = remember { mutableStateListOf<Vocab2ItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot.child(language).child(course).child(lesson).child("Vocabulary").child("Type1").child(question)
            val questionText = questionPath.child("Question").child("Text").getValue(String::class.java) ?: ""
            val questionSound = questionPath.child("Question").child("Sound").getValue(String::class.java) ?: ""
            questionData.add(Vocab2ItemQuestion("Question", questionText, questionSound))

            val commentsSnapshot = questionPath.child("Comments")
            commentsSnapshot.children.forEach { commentSnapshot ->
                commentNumberVocab2++
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
                    answerData.add(Vocab2ItemAnswer(name, correctAnswer, text))
                }
            }

            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }
    return if (dataStatus.value) {
        listOf(QuestionAnswerDataVocab2(true, questionData, answerData, commentsData))
    } else {
        emptyList()
    }
}

@Composable
fun WriteCommentVocab2(language: String, course: String, lesson: String, question: String, newComment: String, commentNumberVocab2: Int) {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val commentPath = courseListRef.child(language).child(course).child(lesson).child("Vocabulary").child("Type1").child(question).child("Comments")

    val newCommentKey = "Text$commentNumberVocab2"
    LaunchedEffect(Unit) {
        commentPath.child(newCommentKey).setValue(newComment)
            .addOnSuccessListener {
                Log.d("FirebaseCheck", "Comment added successfully!")
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseCheck", "Failed to add comment!", exception)
            }
    }
}

@Composable
fun vocab2MP3Storage(location: String, fileName: String): Uri? {
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
fun VocabScreen2(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {

    //FOR EACH VOCAB EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val language = "English"
    val course = "CS"
    val lesson = "Basics of Program Development"
    val question1 = "Q1"
    val vocabData = getDataVocab2(language, course, lesson, question1)

    val questionText: String
    val audioUri: Uri?
    val answerNumber = listOf(0, 1, 2)
    val answerDisplay = listOf(
        mutableListOf<Vocab2ItemAnswer>(),
        mutableListOf<Vocab2ItemAnswer>(),
        mutableListOf<Vocab2ItemAnswer>(),
    )

    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    if (vocabData.isNotEmpty() && vocabData[0].status) {
        val question = vocabData[0].question
        val answer = vocabData[0].answer
        val discussionComments = vocabData[0].comments
        Log.d("FirebaseCheck", "Question List: $question, Answer List: $answer")
        audioUri = vocab2MP3Storage(question[0].sound, "Vocab-Type1-Question-Sound")
        questionText = question[0].text
        val answerOrder = remember { answerNumber.shuffled(Random) }
        for ((i, num) in answerOrder.withIndex()) {
            answerDisplay[num].add(answer[i])
        }

        val context = LocalContext.current
        var cardType1 by remember { mutableStateOf(CardType.WHITE) }
        var cardType2 by remember { mutableStateOf(CardType.WHITE) }
        var cardType3 by remember { mutableStateOf(CardType.WHITE) }
        var selectedCard by remember { mutableIntStateOf(0) }
        var answerResultStatus by remember { mutableStateOf(false) }
        var continueStatus by remember { mutableStateOf(false) }

        Column(
            modifier = modifier.fillMaxSize().background(Color(0xFF073748))
                .padding(start = 20.dp, end = 20.dp).verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
            ) {
                CloseButton(onClick = { ExitLesson() })
                Spacer(modifier = Modifier.width(10.dp))
                LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    })
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 20.dp)
                            ).padding(10.dp),
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
                Spacer(modifier = Modifier.height(60.dp))
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val title1 = answerDisplay[0][0].text
                    Card(
                        title = title1,
                        description = null,
                        cardType = cardType1,
                        cardShape = CardShape.CHOICES_RECTANGLE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[0][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.GREEN
                                cardType2 = CardType.WHITE
                                cardType3 = CardType.WHITE
                                selectedCard = 1

                            } else{
                                cardType1 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = R.drawable.map,
                        imageUri = null
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    val title2 = answerDisplay[1][0].text
                    Card(
                        title = title2,
                        description = null,
                        cardType = cardType2,
                        cardShape = CardShape.CHOICES_RECTANGLE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[1][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.WHITE
                                cardType2 = CardType.GREEN
                                cardType3 = CardType.WHITE
                                selectedCard = 2

                            } else{
                                cardType2 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = R.drawable.map,
                        imageUri = null
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    val title3 = answerDisplay[2][0].text
                    Card(
                        title = title3,
                        description = null,
                        cardType = cardType3,
                        cardShape = CardShape.CHOICES_RECTANGLE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = answerDisplay[2][0].correctAnswer
                            if (answerResultStatus) {
                                cardType1 = CardType.WHITE
                                cardType2 = CardType.WHITE
                                cardType3 = CardType.GREEN
                                selectedCard = 3

                            } else{
                                cardType3 = CardType.RED
                                if (selectedCard != 0){
                                    when(selectedCard){
                                        1 -> cardType1 = CardType.WHITE
                                        2 -> cardType2 = CardType.WHITE
                                        3 -> cardType3 = CardType.WHITE
                                    }
                                }
                            }
                        },
                        imageResId = R.drawable.map,
                        imageUri = null
                    )

                }
                Spacer(modifier = Modifier.height(100.dp))
                MainButton(button = "CONTINUE", onClick = {
                    if (!answerResultStatus){ //IF ANSWER IS NOT CORRECT
                        dialogType = "INCORRECT_ANS"
                        showDialog = true
                    }
                    if (answerResultStatus){
                        continueStatus = true
                    }
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
                if (vocabData.isNotEmpty()) {
                    val comment = vocabData[0].comments
                    if (comment.isNotEmpty()) {
                        newCommentsDataVocab2 = Discussion(comments = discussionComments)
                    }
                }
            }
        }
        if (continueStatus) {
            val addedCommentsNo = newCommentsDataVocab2.size - discussionComments.size
            commentNumberVocab2 = discussionComments.size
            for (i in 0 until addedCommentsNo) {
                commentNumberVocab2++
                val newComment = newCommentsDataVocab2[discussionComments.size + i]
                WriteCommentVocab2(language, course, lesson, question1, newComment, commentNumberVocab2)
            }
            continueStatus = false
            NextExercise()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VocabScreen2Preview() {
    FieldWiseTheme {
        VocabScreen2(
            ExitLesson = {},
            NextExercise = {},
            type = ""
        )
    }
}

