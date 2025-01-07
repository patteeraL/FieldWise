package com.example.fieldwise.ui.screen.home_page

import CourseManageButton
import StreakItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.HomeButton
import com.example.fieldwise.ui.widget.LeaderBoardButton
import com.example.fieldwise.ui.widget.LessonCard
import com.example.fieldwise.ui.widget.ProfileIconButton
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import com.example.fieldwise.core.DatabaseProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material.Text
import com.example.fieldwise.ui.screen.profile_creation.globalCourse
import com.example.fieldwise.ui.screen.profile_creation.globalLanguage


data class CourseFormat(val language: String, val subject: String, val course: String)

var Lesson1 = ""
var Lesson2 = ""
var LessonNo = ""
var CourseABB = ""

@Composable
fun getCourseList(): List<CourseFormat> {
    // back-end
    val database = Firebase.database
    val courseListRef = database.reference.child("Exercises")
    val courseList = remember { mutableStateOf<List<CourseFormat>>(emptyList()) }
    LaunchedEffect(Unit) {
        courseListRef.get().addOnSuccessListener { dataSnapshot ->
            val data = mutableListOf<CourseFormat>()
            for (languageSnapshot in dataSnapshot.children) {
                val language = languageSnapshot.key ?: ""
                for (subjectSnapshot in languageSnapshot.children) {
                    val subject = subjectSnapshot.key ?: ""
                    for (courseSnapshot in subjectSnapshot.children) {
                        val course = courseSnapshot.key ?: ""
                        data.add(CourseFormat(language, subject, course))
                    }
                }
            }
            courseList.value = data
            Log.d("TAG", "Database Extracted Successfully!: ${courseList.value}")
        }.addOnFailureListener { exception ->
            Log.e("TAG", "Database Extraction Error!", exception)
        }
    }
    return courseList.value
}

@Composable
fun getFilteredCourseList(selectedLanguage: String, selectedCourse: String): List<CourseFormat> {
    val allCourses = getCourseList()
    var selectedCourseInput = ""
    if (selectedCourse == "Computer Science"){
        selectedCourseInput = "CS"
        CourseABB = "CS"
    }
    if (selectedCourse == "Geography"){
        selectedCourseInput = "GEO"
        CourseABB = "GEO"
    }
    return allCourses.filter { it.language == selectedLanguage && it.subject == selectedCourseInput }
}

// Get list of data by "val LISTOFDATA = getUserData()"
// Data will be in the format: [CourseFormat(language=English, subject=CS, course=Basics of Program Development), CourseFormat(language=English, subject=CS, course=Basics of Programming Language), CourseFormat(language=English, subject=GEO, course=Basics of Human Geography), CourseFormat(language=English, subject=GEO, course=Basics of World Geography)]
// You can access the data for example -- LISTOFDATA[2].course will return "Basics of Programming Language". Refer to ScoreBoard.kt for example implementation

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               NavigateToLeader: () -> Unit,
               NavigateToAddCourse: () -> Unit,
               NavigateToAddLanguage: () -> Unit,
               NavigateToProfile: () -> Unit,
               NavigateToLessons: () -> Unit,
               NavigateToQuiz: () -> Unit) {
    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)

    // Declare mutable state for language and course
    var LocalLanguage = ""
    var LocalCourse = ""

    // Check and load preferred language if globalLanguage is null or empty
    if (globalLanguage == "") {
        LaunchedEffect(Unit) {
            // Load language from the repository
            LocalLanguage = userRepository.getSavedLanguage() ?: ""
            globalLanguage = LocalLanguage
        }
    }
    else{
        LocalLanguage = globalLanguage
        }

    // Check and load selected course if globalCourse is null or empty
    if (globalCourse == "") {
        LaunchedEffect(Unit) {
            // Load course from the repository
            LocalCourse = userRepository.getSavedCourse() ?: ""
            globalCourse = LocalCourse
        }
    }
    else{
        LocalCourse = globalCourse
        }

    var courseList = getFilteredCourseList(globalLanguage, globalCourse)
    var Lesson1 = "Loading..."
    var Lesson2 = "Loading..."
    if (courseList.isNotEmpty()) {
        Lesson1 = courseList[0].course
        Lesson2 = courseList[1].course
    }
    Box(
        modifier = modifier
            .background(color = Color(0xFF073748))
            .fillMaxSize()
            .padding(20.dp, 50.dp, 20.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().testTag("HomeScreen"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
            ) {
                CourseManageButton(
                    NavigateToAddCourse = NavigateToAddCourse,
                    NavigateToAddLanguage = NavigateToAddLanguage
                )
                Spacer(modifier = Modifier.weight(1f)) // Add spacer to push items to the right
                StreakItem(modifier = Modifier.size(40.dp), steak = 5)
                ProfileIconButton(onClick = { NavigateToProfile() })
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row { LessonCard(title = "Lesson 1", description = Lesson1,cardType = CardType.BLUE, progress = 1f, complete = true, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
                Spacer(modifier = Modifier.height(30.dp))
                Row { LessonCard(title = "Lesson 2", description = Lesson2,cardType = CardType.PURPLE, progress = 1f, complete = false, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                HomeButton(onClick = {  })
            }
            Box {
                LeaderBoardButton(onClick = { NavigateToLeader() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FieldWiseTheme {
        HomeScreen(
            NavigateToLeader = {},
            NavigateToAddCourse = {},
            NavigateToAddLanguage = {},
            NavigateToProfile = {},
            NavigateToLessons = {},
            NavigateToQuiz = {}
        )
    }
}