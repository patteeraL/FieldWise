package com.example.fieldwise.ui.screen.lessons.speaking

import Discussion
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.navigation.ExerciseComplete
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.MicButton
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.TextToSpeechButton

@Composable
fun SpeakingScreen1(modifier: Modifier = Modifier, ExerciseComplete: () -> Unit, ExitLesson: () -> Unit) {

    Column(modifier = modifier.fillMaxSize().background(Color(0xFF073748))
        .padding(start = 20.dp, end = 20.dp).verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            CloseButton(onClick = { ExitLesson() })
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
                    Text(text = "Hello world Lorem Lorem Lorem Lorem",
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(220.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){MicButton(onClick = {/* Open Mic */})}
            Spacer(modifier = Modifier.height(100.dp))
            MainButton(button = "CONTINUE", onClick = { ExerciseComplete() }, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Discussion()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SpeakingScreen1Preview() {
    FieldWiseTheme {
        SpeakingScreen1(
            ExitLesson = {},
            ExerciseComplete = {}
        )
    }
}
