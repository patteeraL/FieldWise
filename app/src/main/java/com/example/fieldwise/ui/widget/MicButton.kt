package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun MicButton(modifier: Modifier = Modifier,
                onClick: () -> Unit) {
    Column(modifier = modifier.height(80.dp).width(90.dp)){
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 10.dp)
                    .background(color = Color(0xFFC34544), shape = RoundedCornerShape(15.dp))
            )
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6060)),
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(15.dp)
            ) {
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.mic_icon),
                    contentDescription = "Icon"
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun MicButtonPreview() {
    FieldWiseTheme {
        MicButton(onClick={})
    }
}