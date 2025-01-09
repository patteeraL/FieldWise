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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.ui.theme.FieldWiseTheme

enum class ProgressType { LIGHT, DARK }

@Composable
fun LinearProgress(modifier: Modifier = Modifier, target: Float?, progressType: ProgressType) {
    val targetValue = target ?: 0f
    val size by animateFloatAsState(
        targetValue = targetValue,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    val linearbarcolor = if (progressType == ProgressType.LIGHT)  Color(0xFFE5E5E5) else Color(0xFF0687A7)
    val progresscolor = if (progressType == ProgressType.LIGHT)  Color(0xFF58CC02) else Color(0xFFFFD333)

    Column() {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth(size),
            horizontalArrangement = Arrangement.End
        ) {
        }
        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(17.dp)
        ) {
            // Background of progress Bar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(linearbarcolor)
            )
            // Progress
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(progresscolor)
                    .animateContentSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinearProgressPreview() {
    FieldWiseTheme {
        LinearProgress(target = 0.4f, progressType = ProgressType.DARK)
    }
}
