package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun FloatButton(
    modifier: Modifier = Modifier,
    paintResId: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(87.dp) // Ensure the button is a square
            .clip(CircleShape), // Apply CircleShape to make it circular
        colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Optional: Set button color
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = paintResId),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FloatButtonPreview() {
    FieldWiseTheme {
        FloatButton(paintResId = R.drawable.home,onClick = { /* Handle click */ })
    }
}