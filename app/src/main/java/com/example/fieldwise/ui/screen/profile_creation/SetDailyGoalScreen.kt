package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.widget.GoBackButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.SetUpButton

@Composable
fun SetDailyGoalScreen(modifier: Modifier = Modifier, NavigateToNotification: () -> Unit, NavigateToUserName: () -> Unit) {
    val options = listOf("5 min / day", "10 min / day", "15 min / day", "20 min / day")
    val descriptions = listOf("Light", "Moderate", "Serious", "Intense")
    Column(modifier = modifier.fillMaxSize()
           .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            GoBackButton(onClick = { NavigateToUserName() })
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.3f)
        }

        Column(modifier = modifier
            .fillMaxHeight()) {
            Spacer(modifier = Modifier.height(30.dp))
            Row{
                Text(
                    text = "Set a daily goal",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(3.dp))
            Row{
                Text(
                    text = "To spend a few minutes each day improving your learning",
                    color = Color(0xB24B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            SetUpButton(options, descriptions, iconResIds = null)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(button = "CONTINUE", onClick = { NavigateToNotification() }, mainButtonType = MainButtonType.BLUE)
        }
    }


}



@Preview(showBackground = true)
@Composable
fun SetDailyGoalPreview() {
    FieldWiseTheme {
        SetDailyGoalScreen(
            NavigateToNotification = {},
            NavigateToUserName = {}
        )
    }
}
