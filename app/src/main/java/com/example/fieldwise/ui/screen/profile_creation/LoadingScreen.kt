package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.ShantellSansFontFamily
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingIcon()
        Spacer(modifier = Modifier.height(75.dp)) // Add spacing between icon and button
        Text( text = "Loading . . .",
            color = Color(0xFFADB0B1),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ShantellSansFontFamily
            ))

    }
}

@Composable
fun LoadingIcon(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.loading_icon),
        contentDescription = "Loading icon",
        modifier = modifier
            .size(250.dp) // Set a default size
    )
}


@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    FieldWiseTheme {
        LoadingScreen()
    }
}