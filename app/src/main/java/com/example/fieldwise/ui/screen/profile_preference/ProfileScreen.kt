package com.example.fieldwise.ui.screen.profile_preference

import StreakItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.ui.zIndex
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.example.fieldwise.ui.widget.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.fieldwise.data.local.entities.UserProfile
import com.example.fieldwise.data.provider.DatabaseProvider
import com.example.fieldwise.ui.screen.home_page.userRank
import com.example.fieldwise.ui.screen.home_page.userStreak
import com.example.fieldwise.ui.screen.profile_creation.globalUsername

data class FriendboardItem(val name: String, val profileImage: Int, val streak: Int)
data class LeaderboardProfile(val name: String, val streak: Int)
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
fun ProfileScreen(modifier: Modifier = Modifier, NavigateToHome: () -> Unit, NavigateToSettings: () -> Unit, NavigateToLeader: () -> Unit, NavigateToAddFriend: () -> Unit) {

    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)

    // Taking userdata from localdatabase
    val userProfile = remember { mutableStateOf<UserProfile?>(null) }

        LaunchedEffect(Unit) {
            val savedUser = userRepository.getUserProfile(globalUsername)
                ?: userRepository.getSavedGlobalUsername()?.let {
                    userRepository.getUserProfile(it)
                }
            globalUsername = savedUser?.username ?: ""
            savedUser?.let {
                userProfile.value = it
            } ?: run {
                // Handle no user profile found case
                userProfile.value = null // Or a default UserProfile
            }
        }

    //animation profile image
    val profAn =  remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        profAn.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 1000,
                easing = LinearOutSlowInEasing
            ))
    }
    Box(
        modifier = modifier
            .background(color = Color(0xFF66DBFF))
            .fillMaxSize().testTag("ProfileScreen"),
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
                onClick = { NavigateToSettings() },
                modifier = modifier
                    .size(40.dp)
                    .testTag("SettingButton"),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gear_setting),
                    contentDescription = "Gear Setting",
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
                    HomeButton(onClick = { NavigateToHome() } )
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = modifier
                            .size(200.dp)
                            .graphicsLayer(rotationY = profAn.value),
                    )
                    LeaderBoardButton(onClick = { NavigateToLeader() })
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
                        userProfile.value?.let { profile ->
                            Text(
                                text = profile.username,
                                fontFamily = SeravekFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp
                            ) } ?: run {
                            Text(
                                text = "Loading user data...",
                                color = Color.Gray,
                                fontFamily = SeravekFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp)
                        }
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
                                    text = "$userRank",
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
                                        text = "$userStreak",
                                        fontFamily = SeravekFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 40.sp
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.streak),
                                        modifier = modifier
                                            .size(40.dp),
                                        contentDescription = "Streak Icon"
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        FriendBoard(NavigateToAddFriend = NavigateToAddFriend)
                    }
                }
            }
        }
    }

}

@Composable
fun FriendBoard(NavigateToAddFriend: () -> Unit) {
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
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "Friends",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Box { AddFriendButton(onClick = { NavigateToAddFriend()}) }
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
        ProfileScreen(
            NavigateToLeader = {},
            NavigateToHome = {},
            NavigateToSettings = {},
            NavigateToAddFriend = {}
        )
    }
}