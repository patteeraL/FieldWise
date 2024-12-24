package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.fieldwise.ui.theme.SeravekFontFamily

enum class CardType { YELLOW, BLUE, RED, GREEN, PURPLE, ORANGE, WHITE_GREEN }
enum class CardShape { SELECT_LESSON, QUIZ_RESUME, SELECT_EXERCISE, CHOICES_SQUARE, CHOICES_RECTANGLE, CHOICES_SMALL }

@Composable
fun Card(
    modifier: Modifier = Modifier,
    title: String?,
    description: String?,
    cardType: CardType,
    cardShape: CardShape,
    progress: Float? = 0f,
    complete: Boolean?,
    onClick: (() -> Unit)?,
    imageResId: Int?
) {
    val actualProgress = progress ?: 0f
    val backgroundColor = getColorForCardType(if (actualProgress == 1f && complete == true) CardType.YELLOW else cardType)
    val shadowColor = getShadowColorForCardType(if (actualProgress == 1f && complete == true) CardType.YELLOW else cardType)
    val textColor = getTextColorForCardType(if (actualProgress == 1f && complete == true) CardType.YELLOW else cardType)

    when (cardShape) {
        CardShape.SELECT_LESSON -> {
            CardBase(
                modifier = modifier
                    .width(369.dp)
                    .height(98.dp),
                title = title ?: "Default Title",
                description = description ?: "Default Description",
                progress = actualProgress,
                cardShape = CardShape.SELECT_LESSON,
                complete = complete ?: false,
                onClick = onClick ?: {},
                backgroundColor = backgroundColor,
                shadowColor = shadowColor,
                textColor = textColor
            )
        }
        CardShape.QUIZ_RESUME -> {
            CardBase(
                modifier = modifier
                    .width(326.dp)
                    .height(45.dp),
                title = title ?: "Default Title",
                description = "",
                progress = actualProgress,
                cardShape = CardShape.QUIZ_RESUME,
                complete = complete ?: false,
                onClick = onClick ?: {},
                backgroundColor = backgroundColor,
                shadowColor = shadowColor,
                textColor = textColor
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    imageResId?.let {
                        Image(
                            modifier = Modifier.size(35.dp),
                            painter = painterResource(id = it),
                            contentDescription = "Icon"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = title ?: "Default Title",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            letterSpacing = 0.25.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        CardShape.CHOICES_SQUARE -> {
            CardBase(
                modifier = modifier.size(160.dp),
                title = title ?: "Default Title",
                description = "",
                progress = actualProgress,
                cardShape = CardShape.CHOICES_SQUARE,
                complete = complete ?: false,
                onClick = onClick ?: {},
                backgroundColor = backgroundColor,
                shadowColor = shadowColor,
                textColor = textColor
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    imageResId?.let {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = it),
                            contentDescription = "Icon"
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = title ?: "Default Title",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            letterSpacing = 0.25.sp
                        )
                    )
                }
            }
        }
        CardShape.SELECT_EXERCISE -> {
            Column(modifier = modifier.width(160.dp).height(200.dp)){
                CardBase(
                modifier = modifier.size(160.dp),
                title = title ?: "Default Title",
                description = "",
                progress = actualProgress,
                cardShape = CardShape.SELECT_EXERCISE,
                complete = complete ?: false,
                onClick = onClick ?: {},
                backgroundColor = backgroundColor,
                shadowColor = shadowColor,
                textColor = textColor
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    imageResId?.let {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = it),
                            contentDescription = "Icon"
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = title ?: "Default Title",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            letterSpacing = 0.25.sp
                        )
                    )

                }

            }
            Spacer(modifier = Modifier.height(25.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = Modifier.width(100.dp)) {
                        LinearProgress(target = progress, progressType = ProgressType.DARK)
                    }
                    Text(
                        text = "${(actualProgress * 100).toInt()}%",
                        color = Color(0xFFFFD333),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SeravekFontFamily
                        )
                    )
                }
        }
        }
        else -> {
            // Handle other shapes if needed
        }
    }
}

@Composable
fun getColorForCardType(cardType: CardType): Color {
    return when (cardType) {
        CardType.YELLOW -> Color(0xFFFFD333)
        CardType.BLUE -> Color(0xFF1AB8E8)
        CardType.RED -> Color(0xFFFF6060)
        CardType.GREEN -> Color(0xFF58CC02)
        CardType.PURPLE -> Color(0xFFCE82FF)
        CardType.ORANGE -> Color(0xFFFFB703)
        CardType.WHITE_GREEN -> Color(0xFFFFFFFF)
    }
}

@Composable
fun getShadowColorForCardType(cardType: CardType): Color {
    return when (cardType) {
        CardType.YELLOW -> Color(0xFFEAB803)
        CardType.BLUE -> Color(0xFF0687A7)
        CardType.RED -> Color(0xFFC34544)
        CardType.GREEN -> Color(0xFF4DAB07)
        CardType.PURPLE -> Color(0xFF8B53AF)
        CardType.ORANGE -> Color(0xFFFB8500)
        CardType.WHITE_GREEN -> Color(0xFFC5C5C5)
    }
}

@Composable
fun getTextColorForCardType(cardType: CardType): Color {
    return when (cardType) {
        CardType.YELLOW -> Color(0xFF524102)
        CardType.WHITE_GREEN -> Color(0xFF58CC02)
        else -> Color.White
    }
}

@Composable
fun CardBase(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    progress: Float,
    complete: Boolean,
    onClick: () -> Unit,
    cardShape: CardShape,
    backgroundColor: Color,
    shadowColor: Color,
    textColor: Color,
    content: @Composable () -> Unit = {
        CardContent(
            title = title,
            description = description,
            progress = progress,
            complete = complete,
            textColor = textColor
        )
    }
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 10.dp)
                .background(color = shadowColor, shape = if (cardShape == CardShape.QUIZ_RESUME) RoundedCornerShape(15.dp) else RoundedCornerShape(20.dp))
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = Modifier.fillMaxSize(),
            shape = if (cardShape == CardShape.QUIZ_RESUME) RoundedCornerShape(15.dp) else RoundedCornerShape(20.dp)
        ) {
            Column(modifier = modifier.fillMaxSize()) {
                content()
            }
        }
    }
}

@Composable
fun CardContent(
    title: String,
    description: String,
    progress: Float,
    complete: Boolean,
    textColor: Color
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        letterSpacing = 0.25.sp
                    )
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = description,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SeravekFontFamily
                    )
                )
            }
            if (progress == 1f && complete == true) {
                Image(
                    painter = painterResource(id = R.drawable.complete),
                    contentDescription = "Complete Mark",
                    modifier = Modifier
                        .size(67.dp)
                        .offset(x = 10.dp, y = (-5).dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(250.dp)) {
                LinearProgress(target = progress, progressType = ProgressType.LIGHT)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                color = textColor,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SeravekFontFamily
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    FieldWiseTheme {
        Card(
            title = "CONTINUE",
            description = "Keep learning every day!",
            cardType = CardType.WHITE_GREEN,
            cardShape = CardShape.QUIZ_RESUME,
            progress = 0.1f,
            complete = true,
            onClick = { /* do logic */ },
            imageResId = R.drawable.map // Replace with your icon resource
        )
    }
}