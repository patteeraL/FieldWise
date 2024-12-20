package com.example.fieldwise.ui.screen.profile_preference

import StreakItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.*

data class FriendboardItem(val name: String, val profileImage: Int, val streak: Int)

// Test Display
val FriendboardData = listOf(
    FriendboardItem("Name1", R.drawable.profile, 10),
    FriendboardItem("Name2", R.drawable.profile1, 10),
    FriendboardItem("Name3", R.drawable.profile2, 10),
    FriendboardItem("Name4", R.drawable.profile1, 9),
    FriendboardItem("Name5", R.drawable.profile2, 8),
    FriendboardItem("Name1", R.drawable.profile, 10),
    FriendboardItem("Name2", R.drawable.profile1, 10),
    FriendboardItem("Name3", R.drawable.profile2, 10),
    FriendboardItem("Name4", R.drawable.profile1, 9),
    FriendboardItem("Name5", R.drawable.profile2, 8),
    FriendboardItem("Name1", R.drawable.profile, 10),
    FriendboardItem("Name2", R.drawable.profile1, 10),
    FriendboardItem("Name3", R.drawable.profile2, 10),
    FriendboardItem("Name4", R.drawable.profile1, 9),
    FriendboardItem("Name5", R.drawable.profile2, 8),
)

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    var showPopup by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(color = Color(0xFF66DBFF))
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 20.dp, end = 30.dp)
                .zIndex(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = { /* Navigate to Setting Page */ },
                modifier = modifier.size(40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gear_setting),
                    contentDescription = "Gear Setting"
                )
            }
        }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(90.dp)) }
            item {
                Row(verticalAlignment = Alignment.Bottom) {
                    HomeButton()
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = modifier.size(200.dp)
                    )
                    LeaderBoardButton(onClick = { })
                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp * 1f)
                        .background(
                            color = Color(0xFFFFFDFD),
                            shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Name1",
                            fontFamily = SeravekFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Rank",
                                    fontFamily = SeravekFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "10",
                                    fontFamily = SeravekFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 40.sp
                                )
                            }
                            Spacer(Modifier.width(50.dp))
                            Row(
                                modifier = Modifier
                                    .width(5.dp)
                                    .height(100.dp)
                                    .background(color = Color(0xFFC5C5C5), shape = CircleShape)
                            ) {}
                            Spacer(Modifier.width(50.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Streak",
                                    fontFamily = SeravekFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                                Spacer(Modifier.height(10.dp))
                                Row {
                                    Text(
                                        text = "0",
                                        fontFamily = SeravekFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 40.sp
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.streak),
                                        modifier = modifier.size(40.dp),
                                        contentDescription = "Streak Icon"
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        FriendBoard(showPopup = showPopup, onShowPopupChange = { showPopup = it })
                    }
                }
            }
        }
    }
    if (showPopup) {
        Box(modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.TopCenter) {
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { showPopup = false }
            ) {
                FriendSearchCard(onDismiss = { showPopup = false })
            }
        }
    }
}

@Composable
fun FriendBoard(showPopup: Boolean, onShowPopupChange: (Boolean) -> Unit) {
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(361.dp)
                .height(70.dp)
                .background(
                    color = Color(0xFF1AB8E8),
                    shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                )
                .zIndex(3f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Friends",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Box { AddFriendButton(onClick = {}, onShowPopupChange = onShowPopupChange) }
            }
        }
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(500.dp)
                .offset(y = (-10).dp)
                .background(
                    color = Color(0xFF0687A7),
                    shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                )
                .zIndex(2f)
        ) {
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(FriendboardData) { item ->
                    FriendboardRow(
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
fun FriendboardRow(name: String, profileImage: Int, streak: Int) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = profileImage),
                contentDescription = "$name's profile image",
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
fun ProfileScreenPreview() {
    FieldWiseTheme {
        ProfileScreen()
    }
}