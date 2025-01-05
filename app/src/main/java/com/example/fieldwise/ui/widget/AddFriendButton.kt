package com.example.fieldwise.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import kotlinx.coroutines.launch


// front-end
@Composable
fun AddFriendButton(modifier: Modifier = Modifier, onClick: () -> Unit) {

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
        },
        modifier = modifier
            .size(40.dp)
            .background(color = Color(0xFF0687A7), shape = CircleShape)
            .graphicsLayer {
                scaleX = buttonAn.value
                scaleY = buttonAn.value
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_friend),
            contentDescription = "Add Friends",
            modifier = Modifier.size(30.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddFriendButtonPreview() {
    FieldWiseTheme {
        AddFriendButton(onClick = {})
    }
}