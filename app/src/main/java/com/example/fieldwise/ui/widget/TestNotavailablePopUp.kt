package com.example.fieldwise.ui.widget


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily

@Composable
fun TestNotavailablePopUp(showDialog: Boolean, onDismiss: () -> Unit) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "You need to complete the exercises to access the test",
                    tint = Color(0xFF555252),
                    modifier = Modifier.size(100.dp)
                )
            },
            title = {
                Text(
                    text = buildAnnotatedString {
                        append("NOT AVAILABLE")
                    },
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black, fontSize = 23.sp, fontFamily = InterFontFamily, fontWeight = FontWeight.Bold))
            },
            text = {Text(
                text = buildAnnotatedString {
                    append("Please complete the exercises to access it.")},
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.Black, fontSize = 18.sp, fontFamily = InterFontFamily)) }
            ,
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
                    Text("Continue", color = Color.White, fontSize = 16.sp,fontFamily = InterFontFamily, fontWeight = FontWeight.Bold) // White text on blue button
                }

            }
        )


    }
}

@Preview(showBackground = true)
@Composable
fun TestNotAvailablePreview() {
    FieldWiseTheme {
        TestNotavailablePopUp(
            showDialog = true,
            onDismiss = {}
        )
    }
}