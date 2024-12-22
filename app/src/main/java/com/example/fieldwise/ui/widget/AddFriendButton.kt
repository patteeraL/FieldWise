package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme


// front-end
@Composable
fun AddFriendButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = {
            onClick()
        },
        modifier = modifier
            .size(40.dp)
            .background(color = Color(0xFF0687A7), shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_friend),
            contentDescription = "Add Friends",
            modifier = Modifier.size(30.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddFriendButtonPreview() {
    FieldWiseTheme {
        AddFriendButton(onClick = {})
    }
}