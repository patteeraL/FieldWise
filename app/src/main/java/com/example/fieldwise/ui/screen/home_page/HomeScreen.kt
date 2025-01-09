package com.example.fieldwise.ui.screen.home_page

import CourseManageButton
import StreakItem
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.core.DatabaseProvider
import com.example.fieldwise.data.UserProgress
import com.example.fieldwise.ui.screen.profile_creation.globalCourse
import com.example.fieldwise.ui.screen.profile_creation.globalLanguage
import com.example.fieldwise.ui.screen.profile_creation.globalUsername
import com.example.fieldwise.ui.screen.profile_preference.LeaderboardProfile
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.HomeButton
import com.example.fieldwise.ui.widget.LeaderBoardButton
import com.example.fieldwise.ui.widget.LessonCard
import com.example.fieldwise.ui.widget.ProfileIconButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


data class CourseFormat(val language: String, val subject: String, val course: String)

var CourseABB = ""
var userRank = 0
var userStreak = 0

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

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               NavigateToLeader: () -> Unit,
               NavigateToAddCourse: () -> Unit,
               NavigateToAddLanguage: () -> Unit,
               NavigateToLoadingScreen2: () -> Unit,
               NavigateToProfile: () -> Unit,
               NavigateToLessons: () -> Unit,
               NavigateToQuiz: () -> Unit) {
    // Initialize local states for global variables
    var localUsername by remember { mutableStateOf(globalUsername) }
    var localLanguage by remember { mutableStateOf(globalLanguage) }
    var localCourse by remember { mutableStateOf(globalCourse) }

    var localProgress1 by remember { mutableStateOf(0.0f) }
    var localProgress2 by remember { mutableStateOf(0.0f) }

    var localProgressStatus = false
    var progress: UserProgress? = UserProgress(
            username = "",
            course = "",
            language = "",
            vocabProgress1 = 0.0f,
            listeningProgress1 = 0.0f,
            speakingProgress1 = 0.0f,
            convoProgress1 = 0.0f,
            vocabProgress2 = 0.0f,
            listeningProgress2 = 0.0f,
            speakingProgress2 = 0.0f,
            convoProgress2 = 0.0f
            )

    LaunchedEffect(localUsername) { globalUsername = localUsername }
    LaunchedEffect(localLanguage) { globalLanguage = localLanguage }
    LaunchedEffect(localCourse) { globalCourse = localCourse }

    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)
    val userProgressRepository = DatabaseProvider.provideUserProgressRepository(context)

    LaunchedEffect(globalUsername) {
        if (globalUsername.isEmpty()) {
            localUsername = userRepository.getSavedGlobalUsername() ?: ""
            globalUsername = localUsername
        }
    }

    LaunchedEffect(globalLanguage) {
        if (globalLanguage.isEmpty()) {
            localLanguage = userRepository.getSavedLanguage() ?: ""
            globalLanguage = localLanguage
        }
    }

    LaunchedEffect(globalCourse) {
        if (globalCourse.isEmpty()) {
            localCourse = userRepository.getSavedCourse() ?: ""
            globalCourse = localCourse
        }
    }

    LaunchedEffect(Unit) {
        progress = userProgressRepository.getUserProgress(globalUsername, globalCourse, globalLanguage)
        if (progress != null) {
            localProgress1 = progress!!.convoProgress1 + progress!!.vocabProgress1 + progress!!.listeningProgress1 + progress!!.speakingProgress1
            localProgress1 = localProgress1/4
            localProgress2 = progress!!.convoProgress2 + progress!!.vocabProgress2 + progress!!.listeningProgress2 + progress!!.speakingProgress2
            localProgress2 = localProgress2/4
            Log.d("CheckingGETTT","$progress")
        } else{
            Log.d("CHECKADDINGNEW","CHECKADDINGNEW")
            userProgressRepository.saveUserProgress(
                username = globalUsername,
                course = globalCourse,
                language = globalLanguage,
                vocabProgress1 = 0.0f,
                listeningProgress1 = 0.0f,
                speakingProgress1 = 0.0f,
                convoProgress1 = 0.0f,
                vocabProgress2 = 0.0f,
                listeningProgress2 = 0.0f,
                speakingProgress2 = 0.0f,
                convoProgress2 = 0.0f
            )
        }

    }

    var LeaderCounter = 0
    val leaderboardData = mutableListOf<LeaderboardProfile>()
    val database = Firebase.database
    val leaderboardRef = database.reference.child("Leaderboard")

    leaderboardRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            leaderboardData.clear()
            LeaderCounter = 0
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { userSnapshot ->
                    val name = userSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val streak = userSnapshot.child("Streak").getValue(Long::class.java) ?: 0L
                    leaderboardData.add(LeaderboardProfile(name, streak.toInt()))
                }
                leaderboardData.sortByDescending { it.streak }
                for (person in leaderboardData) {
                    LeaderCounter++
                    if (person.name == globalUsername) {
                        userRank = LeaderCounter
                        userStreak = person.streak
                        break
                    }
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("FirebaseError", "Error: ${databaseError.message}")
        }
    })

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
                    NavigateToAddLanguage = NavigateToAddLanguage,
                    NavigateToLoadingScreen2 = NavigateToLoadingScreen2,
                )
                Spacer(modifier = Modifier.weight(1f)) // Add spacer to push items to the right
                StreakItem(modifier = Modifier.size(40.dp), steak = userStreak)
                ProfileIconButton(onClick = { NavigateToProfile() })
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row { LessonCard(title = "Lesson 1", description = Lesson1,cardType = CardType.BLUE, progress = localProgress1, complete = true, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
                Spacer(modifier = Modifier.height(30.dp))
                Row { LessonCard(title = "Lesson 2", description = Lesson2,cardType = CardType.PURPLE, progress = localProgress2, complete = false, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
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
            NavigateToLoadingScreen2 = {},
            NavigateToProfile = {},
            NavigateToLessons = {},
            NavigateToQuiz = {}
        )
    }
}