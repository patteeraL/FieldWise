package com.example.fieldwise.ui.screen.lessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.HomeButton
import com.example.fieldwise.ui.widget.LeaderBoardButton

@Composable
fun SelectExerciseScreen(modifier: Modifier = Modifier, NavigateToLeader: () -> Unit, NavigateToHome: () -> Unit, NavigateToListening: () -> Unit, NavigateToConversation: () -> Unit, NavigateToSpeaking: () -> Unit, NavigateToVocabulary: () -> Unit) {
    Box(
        modifier = modifier
            .background(color = Color(0xFF073748))
            .fillMaxSize()
            .padding(20.dp, 30.dp, 20.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.height(65.dp),
                title = "Lesson 2",
                description = "Consumer and Producer Behavior",
                cardType = CardType.GREEN,
                cardShape = CardShape.SELECT_LESSON,
                progress = null,
                complete = false,
                onClick = null,
                imageResId = null
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Column {
                    Card(
                        title = "Speaking",
                        description = null,
                        cardType = CardType.ORANGE,
                        cardShape = CardShape.SELECT_EXERCISE,
                        progress = 0.5f,
                        complete = false,
                        onClick = { NavigateToSpeaking() },
                        imageResId = R.drawable.speaking
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                Card(
                    title = "Listening",
                    description = null,
                    cardType = CardType.GREEN,
                    cardShape = CardShape.SELECT_EXERCISE,
                    progress = 0.5f,
                    complete = false,
                    onClick = { NavigateToListening() },
                    imageResId = R.drawable.listening
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Card(
                    title = "Vocabulary",
                    description = null,
                    cardType = CardType.PURPLE,
                    cardShape = CardShape.SELECT_EXERCISE,
                    progress = 0.5f,
                    complete = false,
                    onClick = { NavigateToVocabulary() },
                    imageResId = R.drawable.vocabulary
                )
                Spacer(modifier = Modifier.width(25.dp))
                Card(
                    title = "Conversation",
                    description = null,
                    cardType = CardType.BLUE,
                    cardShape = CardShape.SELECT_EXERCISE,
                    progress = 0.5f,
                    complete = false,
                    onClick = { NavigateToConversation() },
                    imageResId = R.drawable.conversation
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            QuizCardBase(
                modifier = Modifier
                    .width(369.dp)
                    .height(98.dp),
                title = "Final Test",
                description = "The test will unlock automatically after each session is completed.",
                complete = false, //true if the test is done
                onClick = {/* Go to Quiz */}
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeButton(onClick = { NavigateToHome() })
            LeaderBoardButton(onClick = { NavigateToLeader() })
        }
    }
}

@Composable
fun QuizCardBase(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    complete: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (complete) Color(0xFF58CC02) else Color.White
    val shadowColor = if (complete) Color(0xFF4DAB07) else Color(0xFFC5C5C5)
    val status = if (complete) "Completed!" else "Locked!"

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(369.dp)
                .height(98.dp)
                .offset(y = 10.dp)
                .background(color = shadowColor, shape = RoundedCornerShape(20.dp))
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = Modifier
                .width(369.dp)
                .height(98.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            QuizCardContent(
                status = status,
                title = title,
                description = description,
                complete = complete
            )
        }
    }
}

@Composable
fun QuizCardContent(
    status: String,
    title: String,
    description: String,
    complete: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
            ) {
                Text(
                    text = status,
                    fontSize = 12.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (complete) Color.White else Color(0xFF555252),
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (complete) Color.White else Color.Black,
                    letterSpacing = 0.25.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = description,
                    lineHeight = 1.2.em,
                    color = Color(0x80000000),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = SeravekFontFamily
                )
            }
            VerticalDivider(
                thickness = 3.dp,
                color = if (complete) Color(0xFF4DAB07) else Color(0xFFC5C5C5)
            )
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!complete) {
                    Image(
                        painter = painterResource(id = R.drawable.quiz_notstart),
                        contentDescription = "Quiz Not Start",
                        modifier = Modifier
                            .size(55.dp)
                            .offset(x = 5.dp)
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Not finished",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFD9D9D9),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        color = Color(0x80000000),
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SeravekFontFamily
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.restart),
                        contentDescription = "Restart Icon",
                        modifier = Modifier.size(55.dp)
                    )
                    Spacer(Modifier.height(5.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectExerciseScreenPreview() {
    FieldWiseTheme {
        SelectExerciseScreen(
            NavigateToLeader = {},
            NavigateToHome = {},
            NavigateToListening = {},
            NavigateToConversation = {},
            NavigateToVocabulary = {},
            NavigateToSpeaking = {}
        )
    }
}
