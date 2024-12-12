package com.example.fieldwise.ui.widget

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun LinearProgress(target: Float) {
    var progress by remember { mutableStateOf(0f) }
    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    Column() {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth(size),
            horizontalArrangement = Arrangement.End
        ) {

        }
        //Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(17.dp)
        ) {
            //Background of progress Bar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(0xFFE5E5E5))
            )
            //Progress
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(0xFF58CC02))
                    .animateContentSize()
            )
        }
    }
    LaunchedEffect(key1 = true) {
        progress = target
    }
}

@Preview(showBackground = true)
@Composable
fun LinearProgressPreview() {
    FieldWiseTheme {
        LinearProgress(target = 0.1f)
    }
}
