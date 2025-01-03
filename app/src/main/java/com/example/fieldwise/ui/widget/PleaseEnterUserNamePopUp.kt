package com.example.fieldwise.ui.widget


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import kotlinx.coroutines.delay

@Composable
fun PleaseEnterUserNamePopUp(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        LaunchedEffect(Unit) {
            delay(2000) // Delay for 1000 milliseconds
            onDismiss()
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "Warning Enter a username",
                    tint = Color(0xFFFFB703),
                    modifier = Modifier.size(60.dp)
                )
            },
            title = {
                Text(
                    text = buildAnnotatedString {
                        append("Enter a username to proceed")
                    },
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black, fontSize = 18.sp, fontFamily = InterFontFamily, fontWeight = FontWeight.Bold)
                )
            },
            confirmButton = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PleaseEnterUserNamePreview() {
    FieldWiseTheme {
        PleaseEnterUserNamePopUp(
            showDialog = true,
            onDismiss = {}
        )
    }
}