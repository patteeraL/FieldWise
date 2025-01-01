package com.example.fieldwise.ui.screen.lessons.conversation

import Discussion
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType

data class Message(val text: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen1(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {
    var text by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(Message("Hi, how are you?", false)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF073748))
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CloseButton(onClick = { ExitLesson()})
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
        }
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
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(messages.size) { index ->
                val message = messages[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (message.isUser) Color(0xFF00CCFF) else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(15.dp)
                    ) {
                        Text(
                            text = message.text,
                            color = if (message.isUser) Color.White else Color.Black,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = SeravekFontFamily
                            )
                        )
                    }
                }
                if (message.isUser) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.5.dp,
                                color = Color(0xFF62AAC2),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .width(338.dp)
                            .height(83.dp)
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
                            Text(
                                text = "AI feedback : ",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = SeravekFontFamily
                                )
                            )
                            Text(
                                text = "(( The feedback))",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    fontFamily = SeravekFontFamily
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().height(70.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                placeholder = {
                    Text(
                        text = "Type a message...",
                        color = Color(0xFF0B4D65)
                    )
                },
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
                onClick = {
                    if (text.isNotBlank()) {
                        messages.add(Message(text, true))
                        text = ""
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .width(70.dp)
                    .height(50.dp)
                    .padding(top = 10.dp, end = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CCFF))
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.sendicon),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        MainButton(button = "CONTINUE", onClick = { NextExercise()}, mainButtonType = MainButtonType.BLUE)
        Spacer(modifier = Modifier.height(50.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Discussion(comments = listOf("Hi", "HI", "HI"))
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