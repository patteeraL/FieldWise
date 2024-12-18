package com.example.fieldwise.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun ProfileIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(

        onClick = onClick, // Use the provided onClick parameter
        modifier = modifier // Apply the modifier parameter
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile Item",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileIconButtonPreview() {
    FieldWiseTheme {
        ProfileIconButton(onClick = {  })
    }
}

