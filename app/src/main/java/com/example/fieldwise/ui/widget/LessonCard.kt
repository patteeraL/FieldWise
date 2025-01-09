package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

var LessonNO = ""
var LessonNAME = ""

@Composable
fun LessonCard(modifier: Modifier = Modifier, title: String, description: String, cardType: CardType, progress: Float, complete: Boolean, NavigateToLessons: () -> Unit, NavigateToQuiz: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row {
            Card(
                title = title,
                description = description,
                cardType = cardType,
                cardShape = CardShape.SELECT_LESSON,
                progress = progress,
                complete = complete,
                onClick = { expanded = true },
                imageResId = null,
                imageUri = null
            )
        }

        if (expanded) {
            Row {
                Popup(
                    onDismissRequest = { expanded = false }) {
                    Box {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent), horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier
                                    .width(355.dp)
                                    .offset(y = 10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.roundedtriangle),
                                    contentDescription = null, modifier = Modifier.size(30.dp)
                                )
                            }
                            Row {
                                Column(
                                    modifier = Modifier
                                        .width(355.dp)
                                        .height(155.dp)
                                        .background(
                                            color = Color(0xFF58CC02),
                                            shape = RoundedCornerShape(size = 20.dp)
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Card(
                                        title = "Review",
                                        description = null,
                                        cardType = CardType.WHITE_GREEN,
                                        cardShape = CardShape.QUIZ_RESUME,
                                        progress = null,
                                        complete = null,
                                        onClick = {
                                            LessonNO = title
                                            LessonNAME = description
                                            NavigateToQuiz() },
                                        imageResId = R.drawable.quizicon,
                                        imageUri = null
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))
                                    Card(
                                        title = "Resume",
                                        description = null,
                                        cardType = CardType.YELLOW,
                                        cardShape = CardShape.QUIZ_RESUME,
                                        progress = null,
                                        complete = null,
                                        onClick = {
                                            LessonNO = title
                                            LessonNAME = description
                                            NavigateToLessons() },
                                        imageResId = R.drawable.playicon,
                                        imageUri = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 500)
@Composable
fun LessonCardPreview() {
    FieldWiseTheme {
        LessonCard(title = "Lesson 1", description = "Consumer and Producer Behavior",cardType = CardType.BLUE, progress = 1f, complete = true, NavigateToLessons = {}, NavigateToQuiz = {})
    }
}