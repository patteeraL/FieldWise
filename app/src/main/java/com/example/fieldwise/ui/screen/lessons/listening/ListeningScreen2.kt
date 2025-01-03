package com.example.fieldwise.ui.screen.lessons.listening


import Discussion
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.ExerciseNotCompletePopUp
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.random.Random

// back-end
data class Listen2ItemAnswer(val correctAnswer: Boolean, val text: String, val link:Int)
data class Listen2ItemQuestion(val sound: String, val link: Int)
data class QuestionAnswerDataListen2(val status: Boolean, val question: List<Listen2ItemQuestion>, val answer: List<Listen2ItemAnswer>, val comments: List<String>)
var commentNumberListen2 = 0
var newCommentsDataListen2: List<String> = emptyList()

@Composable
fun getDataListen2(language: String, course: String, lesson: String, question: String):List<QuestionAnswerDataListen2> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val answerData = remember { mutableStateListOf<Listen2ItemAnswer>() }
    val questionData = remember { mutableStateListOf<Listen2ItemQuestion>() }
    val commentsData = remember { mutableStateListOf<String>() }
    val dataStatus = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val questionPath = dataSnapshot.child(language).child(course).child(lesson).child("Listening").child("Type1").child(question)

            questionPath.children.forEach { questionSnapshot ->
                if (questionSnapshot.key?.startsWith("Question") == true) {
                    val sound = questionSnapshot.child("Sound").getValue(String::class.java) ?: ""
                    val link = questionSnapshot.child("Link").getValue(Int::class.java) ?: 0
                    questionData.add(Listen2ItemQuestion(sound, link))
                }
            }

            val commentsSnapshot = questionPath.child("Comments")
            commentsSnapshot.children.forEach { commentSnapshot ->
                commentNumberListen2++
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
                    val correctAnswer = answerSnapshot.child("CorrectAnswer").getValue(Boolean::class.java) ?: false
                    val text = answerSnapshot.child("Text").getValue(String::class.java) ?: ""
                    val link = answerSnapshot.child("Link").getValue(Int::class.java) ?: 0
                    answerData.add(Listen2ItemAnswer(correctAnswer, text, link))
                }
            }
            dataStatus.value = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }
    return if (dataStatus.value) {
        listOf(QuestionAnswerDataListen2(true, questionData, answerData, commentsData))
    } else {
        emptyList()
    }
}

@Composable
fun WriteCommentListen2(language: String, course: String, lesson: String, question1: String, newComment: String, commentNumberVocab2: Int) {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val commentPath = courseListRef.child(language).child(course).child(lesson).child("Listening").child("Type1").child(question1).child("Comments")

    val newCommentKey = "Text$commentNumberListen2"
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
fun listen2MP3Storage(location: String, fileName: String): Uri? {
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun ListeningScreen2(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {
    //FOR EACH LISTENING2 EXERCISE, UPDATE THE PARAMETERS OF LANGUAGE, COURSE, LESSON AND QUESTION TO DISPLAY THE CORRECT EXERCISE
    val language = "English"
    val course = "CS"
    val lesson = "Basics of Program Development"
    val question1 = "Q1"
    val listenData = getDataListen2(language, course, lesson, question1)
    val audioUri1: Uri?
    val audioUri2: Uri?
    val audioUri3: Uri?
    val audioUri4: Uri?

    val answerNumber = listOf(0, 1, 2, 3)
    val answerDisplay = listOf(
        mutableListOf<Listen2ItemAnswer>(),
        mutableListOf<Listen2ItemAnswer>(),
        mutableListOf<Listen2ItemAnswer>(),
        mutableListOf<Listen2ItemAnswer>(),
    )

    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    if (listenData.isNotEmpty() && listenData[0].status) {
        val question = listenData[0].question
        val answer = listenData[0].answer
        val discussionComments = listenData[0].comments
        Log.d("FirebaseCheck", "Question List: $question, Answer List: $answer")
        audioUri1 = listen2MP3Storage(question[0].sound, "listen-Type1-Rec1-Question-Sound")
        audioUri2 = listen2MP3Storage(question[1].sound, "listen-Type1-Rec2-Question-Sound")
        audioUri3 = listen2MP3Storage(question[2].sound, "listen-Type1-Rec3-Question-Sound")
        audioUri4 = listen2MP3Storage(question[3].sound, "listen-Type1-Rec4-Question-Sound")
        val answerOrder = remember { answerNumber.shuffled(Random) }
        for ((i, num) in answerOrder.withIndex()) {
            answerDisplay[num].add(answer[i])
        }
        val context = LocalContext.current
        var buttonColor01 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor02 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor03 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor04 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor11 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor12 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor13 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var buttonColor14 by remember { mutableStateOf(Color(0xFFFFFFFF)) }
        var selectedCard by remember { mutableIntStateOf(0) }

        var buttonStatus01 by remember { mutableStateOf(false) }
        var buttonStatus02 by remember { mutableStateOf(false) }
        var buttonStatus03 by remember { mutableStateOf(false) }
        var buttonStatus04 by remember { mutableStateOf(false) }
        var buttonStatus11 by remember { mutableStateOf(false) }
        var buttonStatus12 by remember { mutableStateOf(false) }
        var buttonStatus13 by remember { mutableStateOf(false) }
        var buttonStatus14 by remember { mutableStateOf(false) }

        var continueStatus by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF073748))
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Listen & Match",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor01 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor01 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor01 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544) ,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            Button(
                                onClick = {
                                    if (!buttonStatus01) {
                                        Log.i("AudioStatus", "PRESSED $audioUri1")
                                        if (audioUri1 != null) {
                                            try {
                                                MediaPlayer().apply {
                                                    setDataSource(context, audioUri1)
                                                    prepare()
                                                    start()
                                                }
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "AudioStatus",
                                                    "Error playing audio: ${e.message}"
                                                )
                                            }
                                        } else {
                                            Log.w("AudioStatus", "Audio file not downloaded yet!")
                                        }
                                        buttonColor01 = Color(0xFF1AB8E8)
                                        if (!buttonStatus02){buttonColor02 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus03){buttonColor03 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus04){buttonColor04 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus11){buttonColor11 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus12){buttonColor12 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus13){buttonColor13 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus14){buttonColor14 = Color(0xFFFFFFFF)}
                                        selectedCard = 1
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor01
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = modifier.fillMaxSize()) {
                                    Image(
                                        painter = painterResource(id = if (buttonColor01 == Color(0xFFFFFFFF)) R.drawable.voiceicon else R.drawable.voiceicon_white),
                                        contentDescription = "Voice Icon"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor11 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor11 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor11 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            val title1 = answerDisplay[0][0].text
                            Button(
                                onClick = {
                                    if(!buttonStatus11) {
                                        if (selectedCard != 0) {
                                        //buttonColor11 = Color(0xFF156D91)
                                        val checkLink = answerDisplay[0][0].link
                                            if (checkLink == selectedCard) { //Check Logic if the selected question matches with the answer or not
                                                when (selectedCard) {
                                                    1 -> {
                                                        buttonColor01 = Color(0xFF58CC02)
                                                        buttonStatus01 = true
                                                    }

                                                    2 -> {
                                                        buttonColor02 = Color(0xFF58CC02)
                                                        buttonStatus02 = true
                                                    }

                                                    3 -> {
                                                        buttonColor03 = Color(0xFF58CC02)
                                                        buttonStatus03 = true
                                                    }

                                                    4 -> {
                                                        buttonColor04 = Color(0xFF58CC02)
                                                        buttonStatus04 = true
                                                    }
                                                }
                                                buttonColor11 = Color(0xFF58CC02)
                                                buttonStatus11 = true
                                                if (!buttonStatus12) {
                                                    buttonColor12 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus13) {
                                                    buttonColor13 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus14) {
                                                    buttonColor14 = Color(0xFFFFFFFF)
                                                }
                                                selectedCard = 0
                                            } else {
                                                when (selectedCard) {
                                                    1 -> if (!buttonStatus01) {
                                                        buttonColor01 = Color(0xFFFF6060)
                                                    }

                                                    2 -> if (!buttonStatus02) {
                                                        buttonColor02 = Color(0xFFFF6060)
                                                    }

                                                    3 -> if (!buttonStatus03) {
                                                        buttonColor03 = Color(0xFFFF6060)
                                                    }

                                                    4 -> if (!buttonStatus04) {
                                                        buttonColor04 = Color(0xFFFF6060)
                                                    }
                                                }
                                                buttonColor11 = Color(0xFFFF6060)
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor11
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = title1,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = SeravekFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            letterSpacing = 0.25.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor02 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor02 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor02 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544) ,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            Button(
                                onClick = {
                                    if (!buttonStatus02){
                                        Log.i("AudioStatus", "PRESSED $audioUri2")
                                        if (audioUri2 != null) {
                                            try {
                                                MediaPlayer().apply {
                                                    setDataSource(context, audioUri2)
                                                    prepare()
                                                    start()
                                                }
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "AudioStatus",
                                                    "Error playing audio: ${e.message}"
                                                )
                                            }
                                        } else {
                                            Log.w("AudioStatus", "Audio file not downloaded yet!")
                                        }
                                        if (!buttonStatus01){buttonColor01 = Color(0xFFFFFFFF)}
                                        buttonColor02 = Color(0xFF1AB8E8)
                                        if (!buttonStatus03){buttonColor03 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus04){buttonColor04 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus11){buttonColor11 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus12){buttonColor12 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus13){buttonColor13 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus14){buttonColor14 = Color(0xFFFFFFFF)}
                                        selectedCard = 2
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor02
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = modifier.fillMaxSize()) {
                                    Image(
                                        painter = painterResource(id = if (buttonColor02 == Color(0xFFFFFFFF)) R.drawable.voiceicon else R.drawable.voiceicon_white),
                                        contentDescription = "Voice Icon"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor12 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor12 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor12 == Color(0xFF156D91)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            val title2 = answerDisplay[1][0].text
                            Button(
                                onClick = {
                                    if (!buttonStatus12) {
                                        if (selectedCard != 0) {
                                        buttonColor12 = Color(0xFF1AB8E8)
                                        val checkLink = answerDisplay[1][0].link
                                            if (checkLink == selectedCard) { //Check Logic if the selected question matches with the answer or not
                                                when (selectedCard) {
                                                    1 -> {
                                                        buttonColor01 = Color(0xFF58CC02)
                                                        buttonStatus01 = true
                                                    }

                                                    2 -> {
                                                        buttonColor02 = Color(0xFF58CC02)
                                                        buttonStatus02 = true
                                                    }

                                                    3 -> {
                                                        buttonColor03 = Color(0xFF58CC02)
                                                        buttonStatus03 = true
                                                    }

                                                    4 -> {
                                                        buttonColor04 = Color(0xFF58CC02)
                                                        buttonStatus04 = true
                                                    }
                                                }
                                                buttonColor12 = Color(0xFF58CC02)
                                                buttonStatus12 = true
                                                if (!buttonStatus11) {
                                                    buttonColor11 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus13) {
                                                    buttonColor13 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus14) {
                                                    buttonColor14 = Color(0xFFFFFFFF)
                                                }
                                                selectedCard = 0
                                            } else {
                                                when (selectedCard) {
                                                    1 -> if (!buttonStatus01) {
                                                        buttonColor01 = Color(0xFFFF6060)
                                                    }

                                                    2 -> if (!buttonStatus02) {
                                                        buttonColor02 = Color(0xFFFF6060)
                                                    }

                                                    3 -> if (!buttonStatus03) {
                                                        buttonColor03 = Color(0xFFFF6060)
                                                    }

                                                    4 -> if (!buttonStatus04) {
                                                        buttonColor04 = Color(0xFFFF6060)
                                                    }
                                                }
                                                buttonColor12 = Color(0xFFFF6060)
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor12
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = title2,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = SeravekFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            letterSpacing = 0.25.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        if (buttonColor03 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor03 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor03 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            Button(
                                onClick = {
                                    if (!buttonStatus03){
                                    Log.i("AudioStatus", "PRESSED $audioUri3")
                                    if (audioUri3 != null) {
                                        try {
                                            MediaPlayer().apply {
                                                setDataSource(context, audioUri3)
                                                prepare()
                                                start()
                                            }
                                        } catch (e: Exception) {
                                            Log.e("AudioStatus", "Error playing audio: ${e.message}")
                                        }
                                    } else {
                                        Log.w("AudioStatus", "Audio file not downloaded yet!")
                                    }
                                    if (!buttonStatus01){buttonColor01 = Color(0xFFFFFFFF)}
                                    if (!buttonStatus02){buttonColor02 = Color(0xFFFFFFFF)}
                                    buttonColor03 = Color(0xFF1AB8E8)
                                    if (!buttonStatus04){buttonColor04 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus11){buttonColor11 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus12){buttonColor12 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus13){buttonColor13 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus14){buttonColor14 = Color(0xFFFFFFFF)}
                                    selectedCard = 3
                                        }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor03

                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = modifier.fillMaxSize()) {
                                    Image(
                                        painter = painterResource(id = if (buttonColor03 == Color(0xFFFFFFFF)) R.drawable.voiceicon else R.drawable.voiceicon_white),
                                        contentDescription = "Voice Icon"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        if (buttonColor13 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor13 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor13 == Color(0xFF156D91)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            val title3 = answerDisplay[2][0].text
                            Button(
                                onClick = {
                                    if (!buttonStatus13) {
                                        if (selectedCard != 0) {
                                        buttonColor13 = Color(0xFF1AB8E8)
                                        val checkLink = answerDisplay[2][0].link
                                            if (checkLink == selectedCard) { //Check Logic if the selected question matches with the answer or not
                                                when (selectedCard) {
                                                    1 -> {
                                                        buttonColor01 = Color(0xFF58CC02)
                                                        buttonStatus01 = true
                                                    }

                                                    2 -> {
                                                        buttonColor02 = Color(0xFF58CC02)
                                                        buttonStatus02 = true
                                                    }

                                                    3 -> {
                                                        buttonColor03 = Color(0xFF58CC02)
                                                        buttonStatus03 = true
                                                    }

                                                    4 -> {
                                                        buttonColor04 = Color(0xFF58CC02)
                                                        buttonStatus04 = true
                                                    }
                                                }
                                                buttonColor13 = Color(0xFF58CC02)
                                                buttonStatus13 = true
                                                if (!buttonStatus11) {
                                                    buttonColor11 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus12) {
                                                    buttonColor12 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus14) {
                                                    buttonColor14 = Color(0xFFFFFFFF)
                                                }
                                                selectedCard = 0
                                            } else {
                                                when (selectedCard) {
                                                    1 -> if (!buttonStatus01) {
                                                        buttonColor01 = Color(0xFFFF6060)
                                                    }

                                                    2 -> if (!buttonStatus02) {
                                                        buttonColor02 = Color(0xFFFF6060)
                                                    }

                                                    3 -> if (!buttonStatus03) {
                                                        buttonColor03 = Color(0xFFFF6060)
                                                    }

                                                    4 -> if (!buttonStatus04) {
                                                        buttonColor04 = Color(0xFFFF6060)
                                                    }
                                                }
                                                buttonColor13 = Color(0xFFFF6060)
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor13
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = title3,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = SeravekFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            letterSpacing = 0.25.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor04 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor04 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor04 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            Button(
                                onClick = {
                                    if (!buttonStatus04) {
                                        Log.i("AudioStatus", "PRESSED $audioUri4")
                                        if (audioUri4 != null) {
                                            try {
                                                MediaPlayer().apply {
                                                    setDataSource(context, audioUri4)
                                                    prepare()
                                                    start()
                                                }
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "AudioStatus",
                                                    "Error playing audio: ${e.message}"
                                                )
                                            }
                                        } else {
                                            Log.w("AudioStatus", "Audio file not downloaded yet!")
                                        }
                                        if (!buttonStatus01){buttonColor01 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus02){buttonColor02 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus03){buttonColor03 = Color(0xFFFFFFFF)}
                                        buttonColor04 = Color(0xFF1AB8E8)
                                        if (!buttonStatus11){buttonColor11 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus12){buttonColor12 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus13){buttonColor13 = Color(0xFFFFFFFF)}
                                        if (!buttonStatus14){buttonColor14 = Color(0xFFFFFFFF)}
                                        selectedCard = 4
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor04
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = modifier.fillMaxSize()) {
                                    Image(
                                        painter = painterResource(id = if (buttonColor04 == Color(0xFFFFFFFF)) R.drawable.voiceicon else R.drawable.voiceicon_white),
                                        contentDescription = "Voice Icon"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Box(modifier = modifier) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = 10.dp)
                                    .background(
                                        color = if (buttonColor14 == Color(0xFFFFFFFF)) Color(0xFFC5C5C5) else if (buttonColor14 == Color(0xFF58CC02)) Color(0xFF4DAB07) else if (buttonColor14 == Color(0xFF1AB8E8)) Color(0xFF039DC4) else Color(0xFFC34544),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                            val title4 = answerDisplay[3][0].text
                            Button(
                                onClick = {
                                    if (!buttonStatus14) {
                                        if (selectedCard != 0) {
                                        buttonColor14 = Color(0xFF1AB8E8)
                                        val checkLink = answerDisplay[3][0].link
                                            if (checkLink == selectedCard) { //Check Logic if the selected question matches with the answer or not
                                                when (selectedCard) {
                                                    1 -> {
                                                        buttonColor01 = Color(0xFF58CC02)
                                                        buttonStatus01 = true
                                                    }

                                                    2 -> {
                                                        buttonColor02 = Color(0xFF58CC02)
                                                        buttonStatus02 = true
                                                    }

                                                    3 -> {
                                                        buttonColor03 = Color(0xFF58CC02)
                                                        buttonStatus03 = true
                                                    }

                                                    4 -> {
                                                        buttonColor04 = Color(0xFF58CC02)
                                                        buttonStatus04 = true
                                                    }
                                                }
                                                buttonColor14 = Color(0xFF58CC02)
                                                buttonStatus14 = true
                                                if (!buttonStatus11) {
                                                    buttonColor11 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus12) {
                                                    buttonColor12 = Color(0xFFFFFFFF)
                                                }
                                                if (!buttonStatus13) {
                                                    buttonColor13 = Color(0xFFFFFFFF)
                                                }
                                                selectedCard = 0
                                            } else {
                                                when (selectedCard) {
                                                    1 -> if (!buttonStatus01) {
                                                        buttonColor01 = Color(0xFFFF6060)
                                                    }

                                                    2 -> if (!buttonStatus02) {
                                                        buttonColor02 = Color(0xFFFF6060)
                                                    }

                                                    3 -> if (!buttonStatus03) {
                                                        buttonColor03 = Color(0xFFFF6060)
                                                    }

                                                    4 -> if (!buttonStatus04) {
                                                        buttonColor04 = Color(0xFFFF6060)
                                                    }
                                                }
                                                buttonColor14 = Color(0xFFFF6060)
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor14
                                ),
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = title4,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = SeravekFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            letterSpacing = 0.25.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(130.dp))
                MainButton(button = "CONTINUE", onClick = {
                    if (!buttonStatus01 || !buttonStatus02 || !buttonStatus03 || !buttonStatus04 || !buttonStatus11 || !buttonStatus12 || !buttonStatus13 || !buttonStatus14){ //IF ANSWER IS NOT CORRECT
                        dialogType = "INCORRECT_ANS"
                        showDialog = true
                    }
                    if (buttonStatus01 && buttonStatus02 && buttonStatus03 && buttonStatus04 && buttonStatus11 && buttonStatus12 && buttonStatus13 && buttonStatus14){
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
                if (listenData.isNotEmpty()) {
                    val comment = listenData[0].comments
                    if (comment.isNotEmpty()) {
                        newCommentsDataListen2 = Discussion(comments = discussionComments)
                    }
                }
            }
        }
        if (continueStatus) {
            val addedCommentsNo = newCommentsDataListen2.size - discussionComments.size
            commentNumberListen2 = discussionComments.size
            for (i in 0 until addedCommentsNo) {
                commentNumberListen2++
                val newComment = newCommentsDataListen2[discussionComments.size + i]
                WriteCommentListen2(language, course, lesson, question1, newComment, commentNumberListen2)
            }
            continueStatus = false
            NextExercise()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListeningScreen2Preview() {
    FieldWiseTheme {
        ListeningScreen2(
            ExitLesson = {},
            NextExercise = {},
            type = ""
        )
    }
}

