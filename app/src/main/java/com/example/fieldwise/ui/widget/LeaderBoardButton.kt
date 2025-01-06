package com.example.fieldwise.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import kotlinx.coroutines.launch

@Composable
fun LeaderBoardButton(
    modifier: Modifier = Modifier,
    onClick:  () -> Unit
) {//animation when click
    val buttonAn = remember { Animatable(1f) }
    val corountineScope = rememberCoroutineScope()

    Button(
        onClick = {
            corountineScope.launch {
                buttonAn.animateTo(
                    targetValue = 0.8f,
                    animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing)
                )
                buttonAn.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing)
                )
            }
            onClick() },
        modifier = modifier
            .size(87.dp) // Ensure the button is a square
            .clip(CircleShape)
            .graphicsLayer {
                scaleX = buttonAn.value
                scaleY = buttonAn.value}
            .testTag("LeaderBoardButton"), // Apply CircleShape to make it circular
        colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Optional: Set button color
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.trophy),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderBoardButtonPreview() {
    FieldWiseTheme {
        LeaderBoardButton(onClick = { })
    }
}