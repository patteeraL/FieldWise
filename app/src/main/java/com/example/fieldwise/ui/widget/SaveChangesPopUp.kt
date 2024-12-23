package com.example.fieldwise.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.screen.profile_preference.SettingScreen
import com.example.fieldwise.ui.theme.FieldWiseTheme


@Composable
fun SaveChangesPopUp(showDialog: Boolean, onDismiss: () -> Unit) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "saving changes",
                    tint = Color(0xFFFFB703),
                    modifier = Modifier.size(50.dp)
                )
            },
            title = {
                Text(
                    text = buildAnnotatedString {
                        append("CHANGES SAVED SUCCESSFULLY")
                    },
                    style = TextStyle(color = Color.Black), fontSize = 23.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00CCFF) // Blue button color
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Continue", color = Color.White, fontSize = 16.sp) // White text on blue button
                }

            }
        )


    }
}

@Preview(showBackground = true)
@Composable
fun SaveChangesPreview() {
    FieldWiseTheme {
        SaveChangesPopUp(
            showDialog = true,
            onDismiss = {}
        )
    }
}