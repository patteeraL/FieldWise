package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.example.fieldwise.ui.theme.ShantellSansFontFamily
import com.example.fieldwise.ui.theme.FieldWiseTheme
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, NavigateToUserName: () -> Unit ) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingIcon()
        Spacer(modifier = Modifier.height(75.dp)) // Add spacing between icon and button
        Text( text = "Loading . . .",
            color = Color(0xFFADB0B1),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ShantellSansFontFamily
            ))

        LaunchedEffect(Unit) {
            delay(3000)
            NavigateToUserName()
        }

    }
}

@Composable
fun LoadingIcon(modifier: Modifier = Modifier){

    //rotating animation for loading icon

    val LoadingRotate = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        LoadingRotate.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000, easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )

        )

    }
    Image(
        painter = painterResource(id = R.drawable.loading_icon),
        contentDescription = "Rotating loading icon",
        modifier = modifier
            .size(250.dp) // Set a default size
            .graphicsLayer { rotationZ = LoadingRotate.value }
            .testTag("LoadingScreen")
    )
}


@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    FieldWiseTheme {
        LoadingScreen { }
    }
}