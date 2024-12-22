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

enum class CardType { YELLOW, BLUE, RED, GREEN, PURPLE, WHITE_GREEN }
enum class CardShape { RECTANGLE, SQUARE, SMALL_RECTANGLE }

@Composable
fun Card(
    modifier: Modifier = Modifier,
    title: String?,
    description: String?,
    cardType: CardType,
    cardShape: CardShape,
    progress: Float?,
    complete: Boolean?,
    onClick: (() -> Unit)?,
    imageResId: Int?
) {


    val backgroundColor = getColorForCardType(if (progress == 1f && complete == true) CardType.YELLOW else cardType)
    val shadowColor = getShadowColorForCardType(if (progress == 1f && complete == true) CardType.YELLOW else cardType)
    val textColor = getTextColorForCardType(if (progress == 1f && complete == true) CardType.YELLOW else cardType)

    if (cardShape == CardShape.RECTANGLE) {
        LessonCardBase(
            modifier = modifier
                .width(369.dp)
                .height(98.dp),
            title = title ?: "Default Title",
            description = description ?: "Default Description",
            progress = progress ?: 0f,
            complete = complete ?: false,
            onClick = onClick ?: {},
            backgroundColor = backgroundColor,
            shadowColor = shadowColor,
            textColor = textColor
        )
    } else if (cardShape == CardShape.SQUARE) {
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(y = 10.dp)
                    .background(color = shadowColor, shape = RoundedCornerShape(20.dp))
            )
            Button(
                onClick = onClick ?: {},
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                modifier = modifier.size(160.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    imageResId?.let {
                        Image(painter = painterResource(id = it), contentDescription = "Icon")
                    }
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
    }
    else if (cardShape == CardShape.SMALL_RECTANGLE) {
        Box(modifier = modifier) {

            Box(
                modifier = Modifier
                    .width(326.dp)
                    .height(45.dp)
                    .offset(y = 7.dp)
                    .background(color = shadowColor, shape = RoundedCornerShape(15.dp))
            )
            Button(
                onClick = onClick ?: {},
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                modifier = modifier
                    .width(326.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(15.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Spacer(modifier = Modifier.weight(1f)) // Space to push the text to the center

                    Text(
                        text = title ?: "Default Title",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            letterSpacing = 0.25.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically) // Center vertically
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Space to maintain the center alignment
                }

            }
            Box(modifier = Modifier.width(326.dp).height(45.dp).padding(horizontal =20.dp, vertical =5.dp)){imageResId?.let {
                Image(modifier = Modifier.size(35.dp),
                    painter = painterResource(id = it),
                    contentDescription = "Icon"
                )
            }}
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
fun LessonCardBase(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    progress: Float,
    complete: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    shadowColor: Color,
    textColor: Color
) {
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
            Column(modifier = modifier.fillMaxSize()) {
                LessonCardContent(
                    title = title,
                    description = description,
                    progress = progress,
                    complete = complete,
                    textColor = textColor
                )
            }
        }
    }
}

@Composable
fun LessonCardContent(
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
                LinearProgress(progress)
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
            cardShape = CardShape.SMALL_RECTANGLE,
            progress = null,
            complete = true,
            onClick = { /* do logic */ },
            imageResId = R.drawable.map // Replace with your icon resource
        )
    }
}