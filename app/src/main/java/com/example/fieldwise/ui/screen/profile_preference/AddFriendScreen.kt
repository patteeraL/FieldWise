package com.example.fieldwise.ui.screen.profile_preference

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.FriendSearchCard

@Composable
fun AddFriendScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {

        ProfileScreen( NavigateToLeader = {},
            NavigateToHome = {},
            NavigateToSettings = {})

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(enabled = false) { /* Block all clicks */ }
        )


        Box(
            modifier = modifier.fillMaxSize().padding(top = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            FriendSearchCard()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AddFriendScreenPreview() {
    FieldWiseTheme {
        AddFriendScreen()
    }
}