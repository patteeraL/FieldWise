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

data class LeaderboardItem(val name: String, val profileImage: Int, val streak: Int)

//sorted data of leaderboard
val leaderboardData = listOf(
    LeaderboardItem("Name1", R.drawable.profile, 10),
    LeaderboardItem("Name2", R.drawable.profile1, 10),
    LeaderboardItem("Name3", R.drawable.profile2, 10),
    LeaderboardItem("Name4", R.drawable.profile1, 9),
    LeaderboardItem("Name5", R.drawable.profile2, 8),
    LeaderboardItem("Name6", R.drawable.profile1, 8),
    LeaderboardItem("Name7", R.drawable.profile2, 8),
    LeaderboardItem("Name8", R.drawable.profile1, 7),
    LeaderboardItem("Name9", R.drawable.profile1, 6),
    LeaderboardItem("Name10", R.drawable.profile1, 5),
    LeaderboardItem("Name5", R.drawable.profile2, 5),
    LeaderboardItem("Name6", R.drawable.profile1, 4),
    LeaderboardItem("Name7", R.drawable.profile2, 4),
    LeaderboardItem("Name8", R.drawable.profile1, 3),
    LeaderboardItem("Name9", R.drawable.profile1, 2),
    LeaderboardItem("Name10", R.drawable.profile1, 1)
)

@Composable
fun ScoreBoard() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.offset(y = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            LeaderboardBox(
                position = 2,
                name = leaderboardData[1].name,
                profileImage = leaderboardData[1].profileImage,
                streak = leaderboardData[1].streak
            )
            LeaderboardBox(
                position = 1,
                name = leaderboardData[0].name,
                profileImage = leaderboardData[0].profileImage,
                streak = leaderboardData[0].streak
            )
            LeaderboardBox(
                position = 3,
                name = leaderboardData[2].name,
                profileImage = leaderboardData[2].profileImage,
                streak = leaderboardData[2].streak
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
            Row(modifier = Modifier.padding(start = 35.dp, top = 10.dp)) {
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