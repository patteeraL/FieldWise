package com.example.fieldwise.ui.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Friend(val ID: String, val name: String, val profileImage: Int, val streak: Int)

// back-end
@Composable
fun getUserDataFriend(): List<Friend> {
    val database = Firebase.database
    val friendRef = database.reference.child("Leaderboard")
    val friendData = remember { mutableStateOf<List<Friend>>(emptyList()) }

  LaunchedEffect(Unit) {
        friendRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = mutableListOf<Friend>()
                for (postSnapshot in dataSnapshot.children) {
                    val id = postSnapshot.key ?: ""
                    val name = postSnapshot.child("Name").getValue(String::class.java) ?: ""
                    val profile = postSnapshot.child("Profile").getValue(Long::class.java) ?: 1L
                    val streak = postSnapshot.child("Streak").getValue(Long::class.java) ?: 0L
                    val profileImage = if (profile == 1L) {
                        R.drawable.profile1
                    } else {
                        R.drawable.profile2
                    }
                    data.add(Friend(id, name, profileImage, streak.toInt()))
                }
                friendData.value = data
                Log.d("FirebaseCheck", "Database Extracted Successfully!: ${friendData.value}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCheck", "Database Extraction Error!", databaseError.toException())
            }
        })
    }

    return friendData.value // return values in format: [Friend(ID=User0001, name=Alice, profileImage=2131165376, streak=10), Friend(ID=User00010, name=Bob, profileImage=2131165375, streak=9)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendSearchCard(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val accountList = getUserDataFriend()
    val filteredList = accountList.filter { it.name.contains(searchText.text, ignoreCase = true) }

    Column(
        modifier = Modifier

            .zIndex(10F),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            GoBackButton(
                modifier = Modifier
                    .size(80.dp)
                    .background(color = Color.White, shape = CircleShape),
                onClick = {/* Navigate Back to Profile Screen */}
            )
        }
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.07f))
        Box(
            modifier = Modifier
                .width(361.dp)
                .height(100.dp)
                .background(
                    color = Color(0xFF1AB8E8),
                    shape = RoundedCornerShape(10.dp)
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
                    text = "Add friend",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .width(330.dp)
                .height(450.dp)
                .offset(y = (-10).dp)
                .background(
                    color = Color(0xFF0687A7),
                    shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                )
                .zIndex(2f)
        ) {
            Column {
                Text(
                    text = "Enter friend's username",
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 10.dp
                    ),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = SeravekFontFamily,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        modifier = modifier
                            .size(300.dp, 55.dp)
                            .border(3.dp, Color(0xFF1AB8E8), RoundedCornerShape(10.dp))
                            .clip(shape = RoundedCornerShape(10.dp)),
                        value = searchText,
                        onValueChange = { searchText = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFF066076),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SeravekFontFamily,
                            fontSize = 20.sp
                        )
                    )
                }

                if (filteredList.isEmpty() || searchText.text.isEmpty()) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No username match",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
                        items(filteredList) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = item.profileImage),
                                        contentDescription = "${item.name}'s profile image",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = item.name,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                                AddFriendButton(onClick = { /* Add Friend to friend board */ })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendSearchCardPreview() {
    FieldWiseTheme {
        FriendSearchCard()
    }
}
