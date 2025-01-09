package com.example.fieldwise.ui.screen.lessons.vocabulary

import Discussion
import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.core.DatabaseProvider
import com.example.fieldwise.ui.screen.home_page.CourseABB
import com.example.fieldwise.ui.screen.profile_creation.selectedCourse
import com.example.fieldwise.ui.screen.profile_creation.preferredLanguage
import com.example.fieldwise.ui.screen.profile_creation.globalUsername
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LessonNAME
import com.example.fieldwise.ui.widget.LessonNO
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random

// back-end
data class Vocab1ItemAnswer(val name: String, val correctAnswer: Boolean, val text: String, val pic: String)
data class Vocab1ItemQuestion(val name: String, val text: String, val sound: String)
data class QuestionAnswerDataVocab1(val status: Boolean, val question: List<Vocab1ItemQuestion>, val answer: List<Vocab1ItemAnswer>, val comments: List<String>)
data class UriInfoVocab1(val uri: Uri, val name: String, val status: Boolean)
var commentNumberVocab1 = 0
var newCommentsDataVocab1: List<String> = emptyList()

@Composable
fun getDataVocab1(language: String, course: String, lesson: String, question: String):List<QuestionAnswerDataVocab1> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val answerData = remember { mutableStateListOf<Vocab1ItemAnswer>() }
    val questionData = remember { mutableStateListOf<Vocab1ItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot.child(language).child(course).child(lesson).child("Vocabulary").child("Type2").child(question)
            val questionText = questionPath.child("Question").child("Text").getValue(String::class.java) ?: ""
            val questionSound = questionPath.child("Question").child("Sound").getValue(String::class.java) ?: ""
            questionData.add(Vocab1ItemQuestion("Question", questionText, questionSound))
            val commentsSnapshot = questionPath.child("Comments")
            commentsSnapshot.children.forEach { commentSnapshot ->
                commentNumberVocab1++
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
                    val pic = answerSnapshot.child("Pic").getValue(String::class.java) ?: ""
                    answerData.add(Vocab1ItemAnswer(name, correctAnswer, text, pic))
                }
            }

            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }
    return if (dataStatus.value) {
        listOf(QuestionAnswerDataVocab1(true, questionData, answerData, commentsData))
    } else {
        emptyList()
    }
}

@Composable
fun vocab1PicStorage(location: String, fileName: String): Uri? {
    val context = LocalContext.current
    val storageReference = Firebase.storage.getReferenceFromUrl(location)
    val fieldWiseDir = File(context.filesDir, "FieldWise")
    if (!fieldWiseDir.exists()) fieldWiseDir.mkdirs()
    val destinationFile = File(fieldWiseDir, fileName)
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
fun WriteCommentVocab1(language: String, course: String, lesson: String, question: String, newComment: String, commentNumberVocab1: Int) {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val commentPath = courseListRef.child(language).child(course).child(lesson).child("Vocabulary").child("Type2").child(question).child("Comments")

    val newCommentKey = "Text$commentNumberVocab1"
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
fun vocab1MP3Storage(location: String, fileName: String): Uri? {
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VocabScreen1(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {
    //Progress bar dynamic
    val progress = when (type) {
        "exercise" -> 0.33f
        "quiz" -> 0.7f
        "resume" -> 0.7f
        else -> 0f
    }
    //FOR EACH VOCAB EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val language = preferredLanguage
    val course = CourseABB
    val lesson = LessonNAME
    val question1 = "Q1"
    val vocabData = getDataVocab1(language, course, lesson, question1)

    val context = LocalContext.current
    val userProgressRepository = DatabaseProvider.provideUserProgressRepository(context)

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
        mutableListOf<UriInfoVocab1?>(null),
        mutableListOf<UriInfoVocab1?>(null),
        mutableListOf<UriInfoVocab1?>(null),
        mutableListOf<UriInfoVocab1?>(null)
    )

    if (vocabData.isNotEmpty() && vocabData[0].status) {
        val discussionComments = vocabData[0].comments
        val question = vocabData[0].question
        val answer = vocabData[0].answer
        Log.d("FirebaseCheck", "Question List: $question, Answer List: $answer")
        audioUri = vocab1MP3Storage(question[0].sound, "Vocab-Type2-Question-Sound")
        questionText = question[0].text
        val answerOrder = remember { answerNumber.shuffled(Random) }
        for ((i, num) in answerOrder.withIndex()) {
            answerDisplay[num].add(answer[i])
        }
        val uriTemp1 = Uri.parse("${vocab1PicStorage(answerDisplay[0][0].pic, "Vocab-Type2-Answer1")}") // Create Uri from pic string
        imageUriList[0][0] = UriInfoVocab1(uriTemp1, answerDisplay[0][0].text, answerDisplay[0][0].correctAnswer)
        val uriTemp2 = Uri.parse("${vocab1PicStorage(answerDisplay[1][0].pic,"Vocab-Type2-Answer2")}")
        imageUriList[1][0] = UriInfoVocab1(uriTemp2, answerDisplay[1][0].text, answerDisplay[1][0].correctAnswer)
        val uriTemp3 = Uri.parse("${vocab1PicStorage(answerDisplay[2][0].pic,"Vocab-Type2-Answer3")}")
        imageUriList[2][0] = UriInfoVocab1(uriTemp3, answerDisplay[2][0].text, answerDisplay[2][0].correctAnswer)
        val uriTemp4 = Uri.parse("${vocab1PicStorage(answerDisplay[3][0].pic,"Vocab-Type2-Answer4")}")
        imageUriList[3][0] = UriInfoVocab1(uriTemp4, answerDisplay[3][0].text, answerDisplay[3][0].correctAnswer)

        var cardType1 by remember { mutableStateOf(CardType.WHITE) }
        var cardType2 by remember { mutableStateOf(CardType.WHITE) }
        var cardType3 by remember { mutableStateOf(CardType.WHITE) }
        var cardType4 by remember { mutableStateOf(CardType.WHITE) }
        var selectedCard by remember { mutableIntStateOf(0) }
        var answerResultStatus by remember { mutableStateOf(false) }
        var continueStatus by remember { mutableStateOf(false) }

        Column(modifier = modifier.fillMaxSize().background(Color(0xFF073748)).testTag("Vocab1Screen")
            .padding(start = 20.dp, end = 20.dp).verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(70.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
            ) {
                CloseButton(onClick = { ExitLesson() })
                Spacer(modifier = Modifier.width(10.dp))
                LinearProgress(target = progress, progressType = ProgressType.DARK)
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
                        Text(
                            modifier = modifier.testTag("QuestionText"),
                            text = questionText,
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center)
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    val imageUri1 = imageUriList[0][0]?.uri
                    val title1 = imageUriList[0][0]?.name ?: "Loading..."
                    Card(
                        modifier = Modifier.testTag("Content1"),
                        title = title1,
                        description = null,
                        cardType = cardType1,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = imageUriList[0][0]?.status ?: false
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
                        imageUri = imageUri1
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    val imageUri2 = imageUriList[1][0]?.uri
                    val title2 = imageUriList[1][0]?.name ?: "Loading..."
                    Card(
                        modifier = Modifier.testTag("Content2"),
                        title = title2,
                        description = null,
                        cardType = cardType2,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = imageUriList[1][0]?.status ?: false
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
                        imageUri = imageUri2
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    val imageUri3 = imageUriList[2][0]?.uri
                    val title3 = imageUriList[2][0]?.name ?: "Loading..."
                    Card(
                        modifier = Modifier.testTag("Content3"),
                        title = title3,
                        description = null,
                        cardType = cardType3,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = imageUriList[2][0]?.status ?: false
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
                        imageUri = imageUri3
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    val imageUri4 = imageUriList[3][0]?.uri
                    val title4 = imageUriList[3][0]?.name ?: "Loading..."
                    Card(
                        modifier = Modifier.testTag("Content4"),
                        title = title4,
                        description = null,
                        cardType = cardType4,
                        cardShape = CardShape.CHOICES_SQUARE,
                        progress = null,
                        complete = null,
                        onClick = {
                            answerResultStatus = imageUriList[3][0]?.status ?: false
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
                        imageUri = imageUri4
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))
                MainButton(
                    button = "CONTINUE",
                    onClick = {
                        if (answerResultStatus){
                            continueStatus = true
                        }
                    },
                    mainButtonType = if (!answerResultStatus) MainButtonType.GREY else MainButtonType.BLUE,
                    isEnable = answerResultStatus
                )

                Spacer(modifier = Modifier.height(50.dp))
                HorizontalDivider(thickness = 2.dp, color = Color.White)
                if (vocabData.isNotEmpty()) {
                    val comment = vocabData[0].comments
                    if (comment.isNotEmpty()) {
                        newCommentsDataVocab1 = Discussion(comments = discussionComments)
                    }
                }
            }
            if (continueStatus) {
                val addedCommentsNo = newCommentsDataVocab1.size - discussionComments.size
                commentNumberVocab1 = discussionComments.size
                for (i in 0 until addedCommentsNo) {
                    commentNumberVocab1++
                    val newComment = newCommentsDataVocab1[discussionComments.size + i]
                    WriteCommentVocab1(language, course, lesson, question1, newComment, commentNumberVocab1)
                }
                continueStatus = false
                CoroutineScope(Dispatchers.IO).launch {
                    val progress = userProgressRepository.getUserProgress(
                        globalUsername,
                        selectedCourse,
                        preferredLanguage
                    )
                    var localVocabprogress1 = 0.0f
                    var localSpeakingProgress1 = 0.0f
                    var localListenProgress1 = 0.0f
                    var localConvoProgress1 = 0.0f
                    var localVocabprogress2 = 0.0f
                    var localSpeakingProgress2 = 0.0f
                    var localListenProgress2 = 0.0f
                    var localConvoProgress2 = 0.0f
                    if (progress != null) {
                        localVocabprogress1 = progress.vocabProgress1
                        localSpeakingProgress1 = progress.speakingProgress1
                        localListenProgress1 = progress.listeningProgress1
                        localConvoProgress1 = progress.convoProgress1
                        localVocabprogress2 = progress.vocabProgress2
                        localSpeakingProgress2 = progress.speakingProgress2
                        localListenProgress2 = progress.listeningProgress2
                        localConvoProgress2 = progress.convoProgress2
                        }
                    if (LessonNO == "Lesson 1"){
                        if (localVocabprogress1 < 1f) {
                            localVocabprogress1 = localVocabprogress1 + 0.5f}
                        userProgressRepository.saveUserProgress(
                            username = globalUsername,
                            course = selectedCourse,
                            language = preferredLanguage,
                            vocabProgress1 = localVocabprogress1,
                            listeningProgress1 = localListenProgress1,
                            speakingProgress1 = localSpeakingProgress1,
                            convoProgress1 = localConvoProgress1,
                            vocabProgress2 = localVocabprogress2,
                            listeningProgress2 = localListenProgress2,
                            speakingProgress2 = localSpeakingProgress2,
                            convoProgress2 = localConvoProgress2)
                    }
                    else{
                        if (localVocabprogress2 < 1f) {
                            localVocabprogress2 = localVocabprogress2 + 0.5f}
                        userProgressRepository.saveUserProgress(
                            username = globalUsername,
                            course = selectedCourse,
                            language = preferredLanguage,
                            vocabProgress1 = localVocabprogress1,
                            listeningProgress1 = localListenProgress1,
                            speakingProgress1 = localSpeakingProgress1,
                            convoProgress1 = localConvoProgress1,
                            vocabProgress2 = localVocabprogress2,
                            listeningProgress2 = localListenProgress2,
                            speakingProgress2 = localSpeakingProgress2,
                            convoProgress2 = localConvoProgress2)
                    }
                }
                NextExercise()
            }
            }
        }

    }

@Preview(showBackground = true)
@Composable
fun VocabScreen1Preview() {
    FieldWiseTheme {
        VocabScreen1(
            ExitLesson = {},
            NextExercise = {},
            type = ""
        )
    }
}

