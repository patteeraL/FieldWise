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
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.Card
import com.example.fieldwise.ui.widget.CardShape
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.FloatButton
import com.example.fieldwise.ui.widget.ProfileIconButton
import java.lang.Boolean.TRUE

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color(0xFF073748))
            .fillMaxSize()
            .padding(20.dp, 50.dp, 20.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
            ) {
                CourseManageButton()
                Spacer(modifier = Modifier.weight(1f)) // Add spacer to push items to the right
                StreakItem(modifier = Modifier.size(40.dp), steak = 5)
                ProfileIconButton(onClick = { /* Add your navigation logic here */ })
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    title = "LESSON 1",
                    description = "Consumer and Producer Behavior",
                    cardType = CardType.YELLOW,
                    cardShape = CardShape.RECTANGLE,
                    progress = 1f,
                    complete = TRUE,
                    onClick = { /* Add your navigation logic here */ },
                    imageResId = null
                )
                Spacer(modifier = Modifier.height(30.dp))
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
                FloatButton(
                    onClick = { /* Handle click */ },
                    paintResId = R.drawable.home
                )
            }
            Box {
                FloatButton(
                    onClick = { /* Handle click */ },
                    paintResId = R.drawable.trophy
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FieldWiseTheme {
        HomeScreen()
    }
}