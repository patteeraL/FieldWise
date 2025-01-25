package com.example.fieldwise.ui.screen.profile_creation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.data.provider.DatabaseProvider
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.widget.GoBackButton
import com.example.fieldwise.ui.widget.LinearProgress
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import com.example.fieldwise.ui.widget.PleaseSelectPopUp
import com.example.fieldwise.ui.widget.ProgressType
import com.example.fieldwise.ui.widget.SetUpButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var selectedCourse = ""
var preferredLanguage = ""

@Composable
fun CourseManageScreen(modifier: Modifier = Modifier, NavigateToComplete: () -> Unit, NavigateToNotification: () -> Unit) {
    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)
    val userProgressRepository = DatabaseProvider.provideUserProgressRepository(context)
    val LanguageCourseRepository = DatabaseProvider.provideLanguageCourseRepository(context)
    val fieldOptions = listOf("Computer Science", "Geography")
    val fieldIconResIds = listOf(
        R.drawable.computer,
        R.drawable.map
    )
    val langOptions = listOf("English", "Thai")
    val langIconResIds = listOf(
        R.drawable.eng_circle,
        R.drawable.thai_circle
    )

    var selectedOption1 by remember  { mutableStateOf("") }
    var selectedOption2 by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
        ) {
            GoBackButton(onClick = { NavigateToNotification() })
            Spacer(modifier = Modifier.width(10.dp))
            LinearProgress(target = 0.7f, progressType = ProgressType.LIGHT)
        }

        Column(modifier = modifier
            .fillMaxHeight()) {
            Spacer(modifier = Modifier.size(30.dp))
            Row{
                Text(
                    text = "I want to learn...",
                    modifier = modifier.testTag("CourseManageScreen"),
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row{
                Text(
                    text = "Field",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            SetUpButton(fieldOptions, descriptions = null, iconResIds = fieldIconResIds, onSelectionChange = {selectedOption1 = it})
            Spacer(modifier = Modifier.height(30.dp))
            Row{
                Text(
                    text = "Language",
                    color = Color(0xFF4B4B4B),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            SetUpButton(langOptions, descriptions = null, iconResIds = langIconResIds, onSelectionChange = {selectedOption2 = it})
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(button = "CONTINUE",
                onClick = {
                    if (selectedOption1.isEmpty() || selectedOption2.isEmpty()) {
                        showDialog = true
                    } else {
                        selectedCourse = selectedOption1
                        preferredLanguage = selectedOption2
                        CoroutineScope(Dispatchers.IO).launch {
                            Log.d("saving","$globalUsername,$selectedCourse,$preferredLanguage")
                            userRepository.insertUserProfile(
                                username = globalUsername,
                                selectedCourse = selectedCourse,
                                preferredLanguage = preferredLanguage,
                                dailyGoal = dailyGoal,
                                notificationsEnabled = false //Notification Status
                            )
                            LanguageCourseRepository.insertLanguageCourse(globalUsername, preferredLanguage, selectedCourse)
                            userProgressRepository.insertUserProgress(
                                username = globalUsername,
                                course = selectedCourse,
                                language = preferredLanguage,
                                vocabProgress1 = 0f,
                                listeningProgress1 = 0f,
                                speakingProgress1 = 0f,
                                convoProgress1 = 0f,
                                vocabProgress2 = 0f,
                                listeningProgress2 = 0f,
                                speakingProgress2 = 0f,
                                convoProgress2 = 0f
                            )
                        }
                        // Navigate after coroutine logic
                        NavigateToComplete()
                    }},
                mainButtonType = MainButtonType.BLUE, isEnable = true)
        }
    }

    if (showDialog) {
        PleaseSelectPopUp(
            showDialog = showDialog,
            onDismiss = {showDialog = false}
        )
    }

}



@Preview(showBackground = true)
@Composable
fun CourseManagePreview() {
    FieldWiseTheme {
        CourseManageScreen(
            NavigateToNotification =  {},
            NavigateToComplete =  {}
        )
    }
}
