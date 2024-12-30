package com.example.fieldwise.ui.screen.lessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType

@Composable
fun ExerciseCompleteScreen(modifier: Modifier = Modifier, NavigateToLesson: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize().background(Color(0xFF073748))
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
                    .fillMaxSize() // Make sure the image takes the full size of the Box
                    .align(Alignment.Center) // Center the image
            )
            Column(
                modifier = modifier.fillMaxHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 170.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){

                Image(
                    painter = painterResource(id=R.drawable.checkicon_blue),
                    contentDescription = "Check Icon Blue",
                    modifier = Modifier
                        .size(150.dp)
                )
                Spacer(modifier = Modifier.height(75.dp))
                Text(
                    text = "Your exercise is complete",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Almost there !",
                    color = Color(0x80FFFFFF),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Image(
                    painter = painterResource(id=R.drawable.streak_inc),
                    contentDescription = "Streak Increase by 1",
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
                MainButton(button = "Return to Home ", onClick = { NavigateToLesson() }, mainButtonType = MainButtonType.GREEN)

            }}

    }
}



@Preview(showBackground = true)
@Composable
fun ExerciseCompletePreview() {
    FieldWiseTheme {
        ExerciseCompleteScreen(
            NavigateToLesson = {}
        )
    }
}

