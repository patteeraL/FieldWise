package com.example.fieldwise.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun MainButton(modifier: Modifier = Modifier, button: String, onClick: () -> Unit, text: String) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(369.dp)
                .height(49.dp)
                .offset(y = 10.dp) // Translate 10dp downward
                .background(
                    color = Color(0xFF039DC4), // Slightly darker color
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Button(
            onClick = { onClick() },

            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CCFF)),
            modifier = Modifier
                .width(369.dp)
                .height(49.dp),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Text(
                text = button,
                color = Color.White,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = InterFontFamily
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainButtonPreview() {
    FieldWiseTheme {
        MainButton(button = "CONTINUE", onClick = {  }, text = "GET STARTED")
    }
}

