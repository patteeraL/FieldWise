package com.example.fieldwise.ui.screen.profile_creation

import android.util.Log
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
import com.example.fieldwise.ui.widget.PleaseEnterUserNamePopUp
import com.example.fieldwise.ui.widget.ProgressType
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


var globalUsername: String = ""

@Composable
fun UsernameScreen(modifier: Modifier = Modifier, NavigateToGoal: () -> Unit) {

    var username by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        LinearProgress(target = 0.1f, progressType = ProgressType.LIGHT)
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
        NameTextField(username = username, onValueChange = { newName -> username = newName})
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        MainButton(button = "CONTINUE",
            onClick = {
                if (username.isEmpty()) {
                    showDialog = true
                } else {
                    globalUsername = username
                    var userListNumber = 0
                    val database = Firebase.database
                    val userListSnapshot = database.reference.child("Leaderboard")
                    userListSnapshot.get().addOnSuccessListener { dataSnapshot ->
                        var isUsernameExists = false
                        dataSnapshot.children.forEach { userSnapshot ->
                            val name = userSnapshot.child("Name").getValue(String::class.java) ?: ""
                            if (globalUsername == name) {
                                isUsernameExists = true
                            }
                            userListNumber++
                        }
                        if (isUsernameExists) {
                            errorMessage = "The username is already in use!"
                            Log.d("Error","Error")
                        } else {
                            userListNumber++
                            val newUserKey = "User$userListNumber"
                            val newUser = mapOf(
                                "Name" to globalUsername,
                                "Profile" to Random.nextInt(1, 3),
                                "Streak" to 0
                            )
                            userListSnapshot.child(newUserKey).setValue(newUser)
                            NavigateToGoal()
                        }
                    }
                } },
            mainButtonType = MainButtonType.BLUE, isEnable = true)

        if (showDialog) {
            PleaseEnterUserNamePopUp(
                showDialog = showDialog,
                onDismiss = {showDialog = false}
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(modifier: Modifier = Modifier, username: String, onValueChange: (String) -> Unit) {


    OutlinedTextField(
        modifier = modifier
            .size(346.dp, 55.dp)
            .border(3.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp)),
        value = username,
        onValueChange = onValueChange,
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
