package com.example.fieldwise.ui.screen.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.GoBackButton
import com.example.fieldwise.ui.widget.ScoreBoard

@Composable
fun LeaderBoardScreen(modifier: Modifier = Modifier, NavigateBack: () -> Unit, type: String?){
    Box(
        modifier = Modifier.zIndex(1f).padding(start = 20.dp, top = 75.dp)){
        GoBackButton(
            modifier = Modifier.size(70.dp).background(color = Color.White, shape = CircleShape),
            onClick = { NavigateBack() })}
    Box(
        modifier = modifier
            .background(color = Color(0xFF66DBFF))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
    Box(
        modifier = modifier
            .testTag("LeaderBoardScreen")
            .width(413.dp)
            .height(1019.dp)
            .offset(y = 200.dp)
            .background(color = Color(0xFFFFFDFD), shape = RoundedCornerShape(200.dp))

    )
        Box(modifier = Modifier.zIndex(0f).padding(top = 100.dp)){
        ScoreBoard()}
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderBoardScreenPreview() {
    FieldWiseTheme {
        LeaderBoardScreen(NavigateBack = {}, type = "")
    }
}