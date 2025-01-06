package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.widget.GoBackButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.ProgressType

@Composable
fun EnableNotifyScreen(modifier: Modifier = Modifier, NavigateToCourse: () -> Unit, NavigateToGoal: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    // Show the notification permission dialog
    NotificationPermissionHandler(
        showDialog = showDialog,
        onDismiss = { showDialog = false } // Dismiss the dialog
    )

    Column(modifier = modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            GoBackButton(onClick = { NavigateToGoal() })
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.5f, progressType = ProgressType.LIGHT)
        }

        Column(modifier = modifier.fillMaxHeight()) {
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Text(
                    text = "Enable notification",
                    modifier = modifier.testTag("EnableNotifyScreen"),
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Row {
                Text(
                    text = "To allow us to send you notifications",
                    color = Color(0xB24B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Notify(onAllowClick = { showDialog = true })
            }
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(button = "CONTINUE", onClick = { NavigateToCourse() },mainButtonType = MainButtonType.BLUE, isEnable = true)
        }
    }
}

@Composable
fun NotificationPermissionHandler(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Notifications, // Use Material Icons
                    contentDescription = "Notification",
                    tint = Color(0xFFFFB703),
                    modifier = Modifier.size(50.dp)
                )
            },
            title = {
                Text(
                    text = buildAnnotatedString {
                        append("Allow ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("FieldWise")
                        }
                        append(" to send you notifications?")
                    },
                    style = TextStyle(color = Color.Black), fontSize = 23.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss() // Dismiss the dialog
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp).testTag("AllowButton"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00CCFF) // Blue button color
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Allow", color = Color.White, fontSize = 16.sp) // White text on blue button
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss() // Dismiss the dialog

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF999999) // Gray button color
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Deny", color = Color.White, fontSize = 16.sp) // White text on gray button
                }
            }
        )
    }
}

@Composable
fun Notify(onAllowClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .width(348.dp)
            .height(350.dp)
            .background(color = Color(0xFFFFFEFE))
            .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
            .border(width = 2.dp, color = Color(0xFFE5E5E5), shape = RoundedCornerShape(size = 30.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Notifications, // Use Material Icons
            contentDescription = "Notification",
            tint = Color(0xFFFFB703),
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text( modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            text = buildAnnotatedString {
                append("Allow ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("FieldWise")
                }
                append(" to send you notifications?")
            },
            style = TextStyle(color = Color.Black), fontSize = 23.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onAllowClick() // Dismiss the dialog when the button is clicked
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 20.dp, end = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00CCFF) // Blue button color
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Allow", color = Color.White, fontSize = 16.sp) // White text on blue button

        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { // Dismiss the dialog when the button is clicked
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 20.dp, end = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF999999) // Gray button color
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Deny", color = Color.White, fontSize = 16.sp) // White text on gray button
        }
    }
}




@Preview(showBackground = true)
@Composable
fun EnableNotifyPreview() {
    FieldWiseTheme {
        EnableNotifyScreen(
            NavigateToCourse = {},
            NavigateToGoal = {}
        )
    }
}
