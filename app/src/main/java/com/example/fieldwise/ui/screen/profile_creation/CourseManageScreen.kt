package com.example.fieldwise.ui.screen.profile_creation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.SetUpButton

@Composable
fun CourseManageScreen(modifier: Modifier = Modifier, NavigateToComplete: () -> Unit) {
    val fieldOptions = listOf("Computer Science", "Geography")
    val fieldIconResIds = listOf(
        R.drawable.computer, // Replace with your actual vector drawable resource
        R.drawable.map
    )
    val langOptions = listOf("Spanish", "Thai")
    val langIconResIds = listOf(
        R.drawable.spanish_circle, // Replace with your actual vector drawable resource
        R.drawable.thai_circle
    )
    Column(modifier = modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft, // Use Material Icons
                contentDescription = "Arrow Left",
                tint = Color(0xFFAFAFAF),
                modifier = Modifier
                    .size(30.dp) // Adjust the size of the icon
            )
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.7f)
        }

        Column(modifier = modifier
            .fillMaxHeight()) {
            Spacer(modifier = Modifier.size(30.dp))
            Row{
                Text(
                    text = "I want to learn...",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row{
                Text(
                    text = "Field",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            SetUpButton(fieldOptions, descriptions = null, iconResIds = fieldIconResIds)
            Spacer(modifier = Modifier.height(30.dp))
            Row{
                Text(
                    text = "Language",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            SetUpButton(langOptions, descriptions = null, iconResIds = langIconResIds)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(button = "CONTINUE", onClick = { NavigateToComplete() }, text = "GET STARTED")
        }
    }


}



@Preview(showBackground = true)
@Composable
fun CourseManagePreview() {
    FieldWiseTheme {
        CourseManageScreen{}
    }
}
