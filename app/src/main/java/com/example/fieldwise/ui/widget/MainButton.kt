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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale

@Composable
fun MainButton(modifier: Modifier = Modifier, button: String, onClick: () -> Unit, text: String) {
    //animation here
    var buttomPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (buttomPressed) 0.9f else 1f,
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    Box(
        modifier = modifier.scale(scale) //apply the animation
    ) {
        Box(
            modifier = Modifier
                .width(369.dp)
                .height(49.dp)
                .offset(y = 10.dp) // Translate 10dp downward
                .background(
                    color = Color(0xFF0687A7), // Slightly darker color AUN FALTA AGREGAR ANIMACIÓN DE REBOTEEEE
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Button(
            onClick = {
                buttomPressed = true
                onClick()
                buttomPressed = false
                      },
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

