package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import kotlinx.coroutines.launch

@Composable
fun CompleteScreen(modifier: Modifier = Modifier, NavigateToHome: () -> Unit) {

    //animation for background image
    val backgroundAn = remember { Animatable(100f) }
    val checkAn = remember { Animatable(0f)}

    LaunchedEffect(Unit) {
            launch {  backgroundAn.animateTo(
                targetValue = 6000f,
                animationSpec = tween(
                    durationMillis = 1500
                )
            ) }

            launch {  checkAn.animateTo(
                targetValue = 200f,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )}
    }


    //content
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp, 50.dp, 20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Make the Box take the full available size
        ) {
            // Background image (fixed)
            Image(
                painter = painterResource(id = R.drawable.bg1),
                contentDescription = "Complete background",
                modifier = Modifier
                    .size(backgroundAn.value.dp) //animated size
                    .fillMaxSize() // Make sure the image takes the full size of the Box
                    .align(Alignment.Center) // Center the image
            )
            Column(
                modifier = modifier.fillMaxHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 170.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){

                Image(
                    painter = painterResource(id=R.drawable.checkicon_green),
                    contentDescription = "Check Icon Blue",
                    modifier = Modifier
                        .size(checkAn.value.dp)
                )
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "Set up is complete!",
                    modifier = modifier.testTag("CompleteScreen"),
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Ready to dive into some learning?",
                    color = Color(0x804B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(100.dp))
                MainButton(modifier = modifier.testTag("ConfirmButton"),button = "I'M READY !", onClick = { NavigateToHome() }, mainButtonType = MainButtonType.BLUE, isEnable = true)

            }}

    }
}



@Preview(showBackground = true)
@Composable
fun CompletePreview() {
    FieldWiseTheme {
        CompleteScreen{}
    }
}

