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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.CloseButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType

@Composable
fun ListeningScreen1(modifier: Modifier = Modifier, ExitLesson: () -> Unit, NextExercise: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF073748))
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            CloseButton(onClick = { ExitLesson() })
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.3f, progressType = ProgressType.DARK)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ListeningButton(onClick = { /* Make Sound */ })
            }
            Spacer(modifier = Modifier.height(40.dp))
            CardRow()
            Spacer(modifier = Modifier.height(30.dp))
            CardRow()
            Spacer(modifier = Modifier.height(60.dp))
            MainButton(button = "CONTINUE", onClick = { NextExercise() }, mainButtonType = MainButtonType.BLUE)
            Spacer(modifier = Modifier.height(50.dp))
            HorizontalDivider(thickness = 2.dp, color = Color.White)
            Discussion()
        }
    }
}


@Composable
fun CardRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ReusableCard()
        Spacer(modifier = Modifier.width(20.dp))
        ReusableCard()
    }
}

@Composable
fun ReusableCard(modifier: Modifier = Modifier) {
    Card(
        title = "Map",
        description = null,
        cardType = CardType.WHITE,
        cardShape = CardShape.CHOICES_SQUARE,
        progress = null,
        complete = null,
        onClick = { /* do logic */ },
        imageResId = null
    )
}

@Composable
fun ListeningButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.height(80.dp).width(90.dp)) {
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 10.dp)
                    .background(color = Color(0xFF0687A7), shape = RoundedCornerShape(15.dp))
            )
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1AB8E8)),
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(15.dp)
            ) {}
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.texttospeechicon),
                    contentDescription = "Icon"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListeningScreen1Preview() {
    FieldWiseTheme {
        ListeningScreen1(
            ExitLesson = {},
            NextExercise = {}
        )
    }
}
