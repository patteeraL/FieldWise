package com.example.fieldwise.ui.screen.lessons.conversation

import Discussion
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.model.ConverseRequest
import com.example.fieldwise.model.Message
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.viewmodel.AiViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * Function to retrieve a conversation script from Firebase Realtime Database.
 * The script is stored in the path: <Language> -> <Course Name> -> Conversation -> <Question>.
 * This function retrieves one particular script at a time.
 *
 * @param language The language of the course.
 * @param course The name of the course.
 * @param question The specific question (Q1, Q2, Q3, etc.) to retrieve.
 * @return A string containing the conversation script.
 */

var commentNumberConvo = 0
var newCommentsDataConvo: List<String> = emptyList()

@Composable
fun getConversationScript1(language: String, course: String, lesson: String, question: String): String {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val scriptData = remember { mutableStateOf("") }
    val dataStatus = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val script = dataSnapshot
                .child(language)
                .child(course)
                .child(lesson)
                .child("Conversation").
                child(question).getValue(String::class.java) ?: ""
            scriptData.value = script
            dataStatus.value = true
            Log.d("FirebaseCheck", "Database Extraction Successful! Extracted Script: $script")
        }.addOnFailureListener { exception ->
            Log.e("FirebaseCheck", "Database Extraction Error!", exception)
            dataStatus.value = false
        }
    }

    return if (dataStatus.value) {
        scriptData.value
    } else {
        ""
    }
}

@Composable
fun getConversationComments(language: String, course: String, lesson: String): List<String> {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val commentsData = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val commentsPath = dataSnapshot.child(language).child(course).child(lesson).child("Conversation")
            val commentsSnapshot = commentsPath.child("Comments")
            commentsSnapshot.children.forEach { commentSnapshot ->
                commentNumberConvo++
                val commentText = commentSnapshot.getValue(String::class.java)
                if (!commentText.isNullOrEmpty()) {
                    commentsData.add(commentText)
                }
            }
            if (commentsData.isEmpty()) {
                commentsData.add("Loading...")
            }
        }
    }
    return commentsData
}

@Composable
fun WriteCommentConvo(language: String, course: String, lesson: String, newComment: String, commentNumberConvo: Int) {
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val commentPath = courseListRef.child(language).child(course).child(lesson).child("Conversation").child("Comments")

    val newCommentKey = "Text$commentNumberConvo"
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

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen1(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?,
    viewModel: AiViewModel = AiViewModel()
) {

    //Progress bar dynamic
    val progress = when (type) {
        "exercise" -> 0.33f
        "quiz" -> 0.6f
        "resume" -> 0.6f
        else -> 0f
    }
    //Variables to change for each exercise
    val convoLanguage = "English"
    val convoCourse = "CS"
    val convoLesson = "Basics of Program Development"
    val convoQuestion = "Q1"

    var userInput by remember { mutableStateOf("") }
    val history = remember { mutableStateListOf<Message>() }
    var isGeneratingReply by remember { mutableStateOf(false) }
    val script = getConversationScript1(convoLanguage, convoCourse, convoLesson, convoQuestion)
    val discussionComments = getConversationComments(convoLanguage, convoCourse, convoLesson)
    var continueStatus by remember { mutableStateOf(false) }
    val conversationFinished = if (!history.isEmpty() && history.last().content.uppercase().contains("@END_CONVERSATION")) true else false
    // map: {"msgText": "feedback" or null}
    var feedbacks = remember { mutableMapOf<String, String?>() }
    val listState = rememberLazyListState()

    LaunchedEffect(history.lastOrNull()) {
        listState.animateScrollToItem((history.size - 1).coerceAtLeast(0))
    }

    LaunchedEffect(Unit) {
        history.add(Message(role = "assistant", content = "Hi, are you ready for today's lesson?" ))
    }

    fun sendMessage(message: String) {
        if (message.isBlank() || isGeneratingReply) return

        history.add(Message(role = "user", content = message))
        // Add temporary AI message
        history.add(Message(role = "assistant", content = "..."))
        isGeneratingReply = true

        val request = ConverseRequest(
            language = "English",
            script = script,
            history = history.dropLast(1) // Remove loading message
        )

        viewModel.converse(request).observeForever { response ->
            if (response.error != null) {
                history.add(Message(role = "assistant", content = response.error))
                isGeneratingReply = false
                return@observeForever
            }

            feedbacks[history.dropLast(1).last().content] = response.feedback
            isGeneratingReply = false
            history.removeAt(history.lastIndex) // Remove loading message
            history.add(Message(role = "assistant", content = response.reply))
        }
    }

    // UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF073748))
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Spacer(modifier = Modifier.height(70.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CloseButton(onClick = ExitLesson)
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = progress, progressType = ProgressType.DARK)
        }

        // Title
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Let's Chat with AI !",
            color = Color.White,
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )
        )

        // Chat Messages
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.weight(1f), state = listState) {
            items(history.size) { index ->
                val message = history[index]
                val feedback: String? = feedbacks[message.content]
                val isUser = message.role == "user"

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .background(
                                color = if (isUser) Color(0xFF00CCFF) else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(15.dp)
                    ) {
                        Text(
                            // replace @someconsecutivestring in the message with a blank string
                            text = message.content.replace(Regex("@[_a-zA-Z]+"), ""),
                            color = if (isUser) Color.White else Color.Black,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = SeravekFontFamily
                            )
                        )
                    }

                    // Display feedback if available
                    if (isUser && feedback != null && !feedback.uppercase().contains("@NO_FEEDBACK")) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.5.dp,
                                    color = Color(0xFF62AAC2),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .defaultMinSize(minWidth = 338.dp)
                                .wrapContentHeight()
                                .background(
                                    color = Color(0xFF24586A),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.lightbulbicon),
                                    contentDescription = "Light Bulb Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    Text(
                                        text = "Feedback:",
                                        color = Color.White,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = SeravekFontFamily
                                        )
                                    )
                                    Text(
                                        text = feedback,
                                        color = Color.White,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Light,
                                            fontFamily = SeravekFontFamily
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Input Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            if (!conversationFinished){
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                enabled = !isGeneratingReply,
                modifier = Modifier.
                    fillMaxWidth()
                        .height(65.dp)
                        .padding(end = 80.dp),
                placeholder = { Text("Type a message...", color = Color(0xFF0B4D65)) },
                textStyle = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontFamily = SeravekFontFamily,
                    letterSpacing = 1.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF092028)
                ),
                shape = RoundedCornerShape(10.dp)
            )

                Button(
                    enabled = (!isGeneratingReply),
                    onClick = {
                        if (userInput.isNotBlank()) {
                            sendMessage(userInput)
                            userInput = ""
                        }
                    },
                    shape = CircleShape,
                    modifier = modifier
                        .size(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF00CCFF), disabledContainerColor =  Color(0xFFCACACA) )
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.sendicon),
                        contentDescription = null
                    )
                }}

        }

        // Footer
        Spacer(modifier = Modifier.height(20.dp))
        MainButton(
            button = "CONTINUE",
            onClick = {
                continueStatus = true
                },
            mainButtonType = if (conversationFinished) MainButtonType.BLUE else MainButtonType.GREY,
            isEnable = conversationFinished
        )
        Spacer(modifier = Modifier.height(50.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.White)
            if (discussionComments.isNotEmpty()) {
                newCommentsDataConvo = Discussion(comments = discussionComments)
            }
        }
    if (continueStatus) {
        val addedCommentsNo = newCommentsDataConvo.size - discussionComments.size
        commentNumberConvo = discussionComments.size
        for (i in 0 until addedCommentsNo) {
            commentNumberConvo++
            val newComment = newCommentsDataConvo[discussionComments.size + i]
            WriteCommentConvo(convoLanguage, convoCourse, convoLesson, newComment, commentNumberConvo)
        }
        continueStatus = false
        NextExercise()
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationScreen1Preview() {
    FieldWiseTheme {
        ConversationScreen1(
            ExitLesson = {},
            NextExercise = {},
            type = ""
        )
    }
}

