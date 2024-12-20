package com.example.fieldwise.ui.screen.profile_creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType

@Composable
fun UsernameScreen(modifier: Modifier = Modifier, NavigateToGoal: () -> Unit) {

    Column(modifier = modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        LinearProgress(target = 0.1f)
    }
    Column(
        modifier = modifier.fillMaxHeight()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Profile()
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between icon and button
        Text(
            text = "What’s your name?",
            color = Color(0xFF4B4B4B),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        NameTextField()
        Spacer(modifier = Modifier.height(16.dp))
        MainButton(button = "CONTINUE", onClick = { NavigateToGoal() }, mainButtonType = MainButtonType.BLUE)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier
            .size(346.dp, 55.dp)
            .border(3.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp)),
        value = username,
        onValueChange = { username = it },
        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent, // Avoid conflicting background
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent),
        textStyle = TextStyle(color = Color(0xFF000000), fontWeight = FontWeight.Bold, fontFamily = InterFontFamily, fontSize = 19.sp)
    )
}
@Composable
fun Profile(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = "Profile",
        modifier = modifier
            .size(250.dp) // Set a default size
    )
}




@Preview(showBackground = true)
@Composable
fun UsernamePreview() {
    FieldWiseTheme {
        UsernameScreen{}
    }
}
