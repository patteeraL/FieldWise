package com.example.fieldwise.ui.widget

import StreakItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.infiniteRepeatable
import kotlinx.coroutines.launch

//Back-End

data class LeaderboardItem(val name: String, val profileImage: Int, val streak: Int)

@Composable
fun getUserDataLeaderboard(): List<LeaderboardItem> {
    val database = Firebase.database
    val leaderboardRef = database.reference.child("Leaderboard")
    val leaderboardData = remember { mutableStateOf<List<LeaderboardItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        leaderboardRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = mutableListOf<LeaderboardItem>()
                for (postSnapshot in dataSnapshot.children) {
                    val name = postSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val profile = postSnapshot.child("Profile").getValue(Long::class.java) ?: 1L
                    val streak = postSnapshot.child("Streak").getValue(Long::class.java) ?: 0L
                    val profileImage = if (profile == 1L) {
                        R.drawable.profile1
                    } else {
                        R.drawable.profile2
                    }
                    data.add(LeaderboardItem(name, profileImage, streak.toInt()))
                }
                data.sortByDescending { it.streak }
                leaderboardData.value = data
                Log.d("FirebaseCheck", "Database Extracted Successfully!: ${leaderboardData.value}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCheck", "Database Extraction Error!", databaseError.toException())
            }
        })
    }

    return leaderboardData.value
}

@Composable
fun ScoreBoard() {
    val leaderboardData = getUserDataLeaderboard()
    //animation for box scoreboard
    val box1 = remember { Animatable(1000f) }


    LaunchedEffect(Unit) {
        //animation of each box according to the position
        launch {  box1.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000)
        )}

    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .offset(y = box1.value.dp)
                .zIndex(2f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            LeaderboardBox(
                position = 2,
                name = if (leaderboardData.size > 1) leaderboardData[1].name else "",
                profileImage = if (leaderboardData.size > 1) leaderboardData[1].profileImage else R.drawable.profile1,
                streak = if (leaderboardData.size > 1) leaderboardData[1].streak else 0
            )
            LeaderboardBox(
                position = 1,
                name = if (leaderboardData.isNotEmpty()) leaderboardData[0].name else "",
                profileImage = if (leaderboardData.isNotEmpty()) leaderboardData[0].profileImage else R.drawable.profile1,
                streak = if (leaderboardData.isNotEmpty()) leaderboardData[0].streak else 0
            )
            LeaderboardBox(
                position = 3,
                name = if (leaderboardData.size > 2) leaderboardData[2].name else "",
                profileImage = if (leaderboardData.size > 2) leaderboardData[2].profileImage else R.drawable.profile1,
                streak = if (leaderboardData.size > 2) leaderboardData[2].streak else 0
            )
        }

        Box(
            modifier = Modifier
                .width(361.dp)
                .height(70.dp)
                .background(
                    color = Color(0xFF1AB8E8),
                    shape = RoundedCornerShape(5.dp)
                )
                .zIndex(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Leaderboard",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = SeravekFontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .width(350.dp)
                .height(1000.dp)
                .offset(y = (-10).dp)
                .background(
                    color = Color(0xFF0687A7),
                    shape = RoundedCornerShape(30.dp)
                )
                .zIndex(2f)
        ) {
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(leaderboardData.drop(3)) { item ->
                    LeaderboardRow(
                        position = leaderboardData.indexOf(item) + 1,
                        name = item.name,
                        profileImage = item.profileImage,
                        streak = item.streak
                    )
                }
            }
        }
    }
}


// Front-End
@Composable
fun LeaderboardBox(position: Int, name: String, profileImage: Int, streak: Int) {
    val boxHeight = when (position) {
        1 -> 220.dp
        2 -> 180.dp
        else -> 170.dp
    }
    val boxColor = when (position) {
        1 -> Color(0xFFFFD333)
        2 -> Color(0xFFECECEC)
        else -> Color(0xFFFB7C2E)
    }
    val imageSize = when (position) {
        1 -> 100.dp
        2 -> 90.dp
        else -> 80.dp
    }
    val offsetX = when (position) {
        1 -> (-5).dp
        2 -> 20.dp
        else -> (-20).dp
    }

    Column(
        modifier = Modifier
            .zIndex(if (position == 1) 2f else 0f)
            .offset(x = offsetX),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontFamily = SeravekFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )
        Row(modifier = Modifier.padding(bottom = 20.dp).offset(x = 15.dp)) {
            Image(
                painter = painterResource(id = profileImage),
                contentDescription = "$position place profile",
                modifier = Modifier.size(imageSize)
            )
            StreakItem(
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-30).dp, y = 60.dp),
                steak = streak
            )
        }
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(boxHeight)
                .background(
                    color = boxColor,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Row(modifier = Modifier.padding(start = 38.dp, top = 10.dp)) {
                Column(
                    modifier = Modifier
                        .size(43.dp)
                        .background(
                            color = Color(0xFFFFB703),
                            shape = CircleShape
                        )
                        .zIndex(4f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = position.toString(),
                        fontFamily = SeravekFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFB8500),
                        fontSize = 35.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderboardRow(position: Int, name: String, profileImage: Int, streak: Int) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = position.toString(),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = SeravekFontFamily,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = profileImage),
                contentDescription = "$position place profile",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                fontFamily = SeravekFontFamily,
                color = Color.White
            )
        }
        StreakItem(modifier = Modifier.size(30.dp), steak = streak)
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreBoardPreview() {
    FieldWiseTheme {
        ScoreBoard()
    }
}