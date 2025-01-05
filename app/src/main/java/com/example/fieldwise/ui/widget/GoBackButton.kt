package com.example.fieldwise.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.example.fieldwise.ui.theme.FieldWiseTheme
import kotlinx.coroutines.launch

@Composable
fun GoBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    //animation when click
    val buttonAn = remember { Animatable(1f) }
    val corountineScope = rememberCoroutineScope()

    IconButton(
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
            onClick()
        }, // Use the provided onClick parameter
        modifier = modifier
            // Apply the modifier parameter
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .graphicsLayer {
                scaleX = buttonAn.value
                scaleY = buttonAn.value
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoBackButtonPreview() {
    FieldWiseTheme {
        GoBackButton(onClick = {  })
    }
}

