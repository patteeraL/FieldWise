package com.example.fieldwise.ui.screen.lessons.listening


import Discussion
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

@Composable
fun ListeningScreen2(
    modifier: Modifier = Modifier,
    ExitLesson: () -> Unit,
    NextExercise: () -> Unit,
    type: String?
) {

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFF073748))
        .padding(start = 20.dp, end = 20.dp)
        .verticalScroll(rememberScrollState())) {
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start){
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
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Box(
                    modifier
                        .width(150.dp)
                        .height(40.dp)) {
                    VoiceCard(onClick = {})
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier
                    .width(150.dp)
                    .height(40.dp)) {
                    ChoiceCard(onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Box(
                    modifier
                        .width(150.dp)
                        .height(40.dp)) {
                    VoiceCard(onClick = {})
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier
                    .width(150.dp)
                    .height(40.dp)) {
                    ChoiceCard(onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Box(
                    modifier
                        .width(150.dp)
                        .height(40.dp)) {
                    VoiceCard(onClick = {})
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier
                    .width(150.dp)
                    .height(40.dp)) {
                    ChoiceCard(onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Box(
                    modifier
                        .width(150.dp)
                        .height(40.dp)) {
                    VoiceCard(onClick = {})
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier
                    .width(150.dp)
                    .height(40.dp)) {
                    ChoiceCard(onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(130.dp))
            MainButton(button = "CONTINUE", onClick = { NextExercise() }, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Discussion()
        }
    }

}

@Composable
fun VoiceCard(modifier: Modifier = Modifier, onClick: (() -> Unit)){
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 10.dp)
                .background(
                    color = Color(0xFFC5C5C5),
                    shape = RoundedCornerShape(10.dp)
                )
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.voiceicon),
                    contentDescription = "Voice Icon")
            }
        }
    }
}
@Composable
fun ChoiceCard(modifier: Modifier = Modifier, onClick: (() -> Unit)){
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 10.dp)
                .background(
                    color = Color(0xFFC5C5C5),
                    shape = RoundedCornerShape(10.dp)
                )
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Default",
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

