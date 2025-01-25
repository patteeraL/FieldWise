package com.example.fieldwise.ui.screen.home_page

import CourseManageButton
import StreakItem
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.fieldwise.data.provider.DatabaseProvider
import com.example.fieldwise.ui.screen.profile_creation.dailyGoal
import com.example.fieldwise.ui.screen.profile_creation.globalUsername
import com.example.fieldwise.ui.screen.profile_creation.preferredLanguage
import com.example.fieldwise.ui.screen.profile_creation.selectedCourse
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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
    val selectedCourseInput = when (selectedCourse) {
        "Computer Science" -> "CS"
        "Geography" -> "GEO"
        else -> ""
    }
    CourseABB = selectedCourseInput
    return allCourses.filter { it.language == selectedLanguage && it.subject == selectedCourseInput }
}

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
    val localUsername by remember { mutableStateOf(globalUsername) }
    val localLanguage by remember { mutableStateOf(preferredLanguage) }
    val localCourse by remember { mutableStateOf(selectedCourse) }
    val localDailyGoal by remember { mutableStateOf(dailyGoal) }

    var localProgress1 by remember { mutableStateOf(0.0f) }
    var localProgress2 by remember { mutableStateOf(0.0f) }

    LaunchedEffect(localUsername) { globalUsername = localUsername }
    LaunchedEffect(localLanguage) { preferredLanguage = localLanguage }
    LaunchedEffect(localCourse) { selectedCourse = localCourse }
    LaunchedEffect(localDailyGoal) { dailyGoal = localDailyGoal }

    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)
    val userProgressRepository = DatabaseProvider.provideUserProgressRepository(context)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            if (globalUsername.isEmpty()) {
                globalUsername = userRepository.getSavedGlobalUsername().toString()
            }
            val userData = userRepository.getUserProfile(globalUsername)
            if (preferredLanguage.isEmpty()) {
                if (userData != null) {
                    preferredLanguage = userData.preferredLanguage
                }
            }
            if (selectedCourse.isEmpty()) {
                if (userData != null) {
                    selectedCourse = userData.selectedCourse
                }
            }
            val progress = userProgressRepository.getUserProgress(globalUsername, selectedCourse, preferredLanguage)
            Log.d("CheckingGETTT","$progress")
            if (progress != null) {
                localProgress1 = progress.convoProgress1 + progress.vocabProgress1 + progress.listeningProgress1 + progress.speakingProgress1
                localProgress1 /= 4
                localProgress2 = progress.convoProgress2 + progress.vocabProgress2 + progress.listeningProgress2 + progress.speakingProgress2
                localProgress2 /= 4
            }
        }
    }

    var leaderCounter: Int
    val leaderboardData = mutableListOf<LeaderboardProfile>()
    val database = Firebase.database
    val leaderboardRef = database.reference.child("Leaderboard")

    leaderboardRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            leaderboardData.clear()
            leaderCounter = 0
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { userSnapshot ->
                    val name = userSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val streak = userSnapshot.child("Streak").getValue(Long::class.java) ?: 0L
                    leaderboardData.add(LeaderboardProfile(name, streak.toInt()))
                }
                leaderboardData.sortByDescending { it.streak }
                for (person in leaderboardData) {
                    leaderCounter++
                    if (person.name == globalUsername) {
                        userRank = leaderCounter
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

    val lessonList = getFilteredCourseList(preferredLanguage, selectedCourse)
    var lesson1 = "Loading..."
    var lesson2 = "Loading..."
    if (lessonList.isNotEmpty()) {
        lesson1 = lessonList[0].course
        lesson2 = lessonList[1].course
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
                Row { LessonCard(title = "Lesson 1", description = lesson1,cardType = CardType.BLUE, progress = localProgress1, complete = true, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
                Spacer(modifier = Modifier.height(30.dp))
                Row { LessonCard(title = "Lesson 2", description = lesson2,cardType = CardType.PURPLE, progress = localProgress2, complete = false, NavigateToLessons = NavigateToLessons, NavigateToQuiz = NavigateToQuiz)}
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