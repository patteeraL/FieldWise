package com.example.fieldwise.ui.screen.lessons.vocabulary

import Discussion
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton

@Composable
fun VocabScreen2(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {

    Column(modifier = modifier.fillMaxSize().background(Color(0xFF073748))
        .padding(start = 20.dp, end = 20.dp).verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            CloseButton(onClick = { ExitLesson()})
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = modifier
            .fillMaxSize()) {
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                TextToSpeechButton(onClick = {/* Make Text to Speech */} )
                Spacer(modifier = Modifier.width(20.dp))
                Column ( modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp)).padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Hello world Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem",
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Column (modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Card(
                    title = "Map",
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_RECTANGLE,
                    progress = null,
                    complete = null,
                    onClick = { /* do logic */ },
                    imageResId = R.drawable.map,
                    imageUri = null
                )
                Spacer(modifier = Modifier.height(40.dp))
                Card(
                    title = "Map",
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_RECTANGLE,
                    progress = null,
                    complete = null,
                    onClick = { /* do logic */ },
                    imageResId = R.drawable.map,
                    imageUri = null
                )
                Spacer(modifier = Modifier.height(40.dp))
                Card(
                    title = "Map",
                    description = null,
                    cardType = CardType.WHITE,
                    cardShape = CardShape.CHOICES_RECTANGLE,
                    progress = null,
                    complete = null,
                    onClick = { /* do logic */ },
                    imageResId = R.drawable.map,
                    imageUri = null
                )

            }
            Spacer(modifier = Modifier.height(100.dp))
            MainButton(button = "CONTINUE", onClick = { NextExercise()}, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Discussion()
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

