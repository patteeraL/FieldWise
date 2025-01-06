package com.example.fieldwise.ui.screen.profile_creation

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import com.example.fieldwise.data.UserRepository //for localdatabase
import com.example.fieldwise.data.UserProfile
import com.example.fieldwise.core.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


var globalUsername: String = ""

@Composable
fun UsernameScreen(modifier: Modifier = Modifier, NavigateToGoal: () -> Unit) {
    val context = LocalContext.current //

    var username by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(50.dp))
        LinearProgress(target = 0.1f, progressType = ProgressType.LIGHT)
    }
    Column(
        modifier = modifier.fillMaxHeight()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Profile()
        Spacer(modifier = Modifier.height(30.dp)) // Add spacing between icon and button
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
                            if (globalUsername == "FieldWiseADMIN"){
                                NavigateToGoal()
                            }
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

                            //initial values for a new user
                            val userRepository = DatabaseProvider.provideUserRepository(context)
                            val newUserProfile = UserProfile(
                                username = globalUsername,
                                selectedCourse = "",
                                preferredLanguage = "",
                                dailyGoal = 5,
                                notificationsEnabled = true,
                                streak = 0
                            )

                            //saving user in local database
                            CoroutineScope(Dispatchers.IO).launch {
                                userRepository.saveUserProfile(newUserProfile)
                            }

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
            .border(3.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
            .testTag("OutlinedTextField"),
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

    // bounce animation for profile icon

    val profileAn = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        profileAn.animateTo(
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = {OvershootInterpolator(4f).getInterpolation(it)}
                ),
                repeatMode = RepeatMode.Reverse
            )


        )

    }



    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = "Profile",
        modifier = modifier
            .size(250.dp) // Set a default size
            .offset(y = profileAn.value.dp)
            .testTag("UsernameScreen")
    )
}




@Preview(showBackground = true)
@Composable
fun UsernamePreview() {
    FieldWiseTheme {
        UsernameScreen{}
    }
}
