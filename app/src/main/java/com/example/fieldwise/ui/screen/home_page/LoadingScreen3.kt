package com.example.fieldwise.ui.screen.home_page

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.ShantellSansFontFamily
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen3(modifier: Modifier = Modifier, NavigateToHome: () -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background layer
        HomeScreen(
            NavigateToLeader = {},
            NavigateToAddCourse = {},
            NavigateToAddLanguage = {},
            NavigateToLoadingScreen2 = {},
            NavigateToProfile = {},
            NavigateToLessons = {},
            NavigateToQuiz = {}
        )

        // Overlay layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(enabled = false) { /* Block all clicks */ }
        )

        // Loading content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingIcon3()
            }
        }

        // Navigate after delay
        LaunchedEffect(Unit) {
            delay(3000)
            NavigateToHome()
        }
    }
}

@Composable
fun LoadingIcon3(modifier: Modifier = Modifier) {
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
        contentDescription = "Loading icon with rotation animation",
        modifier = modifier
            .size(250.dp)
            .graphicsLayer { rotationZ = LoadingRotate.value }
            .testTag("LoadingScreen")
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreen3Preview() {
    FieldWiseTheme {
        LoadingScreen3(NavigateToHome = {})
    }
}


