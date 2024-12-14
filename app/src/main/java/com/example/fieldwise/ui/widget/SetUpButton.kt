package com.example.fieldwise.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun SetUpButton(options: List<String>, descriptions: List<String>?, iconResIds: List<Int>?) {
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Column() {
        // Loop through the options list and create buttons dynamically
        options.forEachIndexed { index, option ->
            Button(
                onClick = { selectedButton = index }, // Set state to the current button index
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedButton == index) Color(0xFFECFBFF) else Color.Transparent
                ),
                modifier = Modifier
                    .width(365.dp)
                    .height(69.dp)
                    .offset(y = 0.dp),
                shape = RoundedCornerShape(
                    topStart = if (index == 0) 16.dp else 0.dp,
                    topEnd = if (index == 0) 16.dp else 0.dp,
                    bottomStart = if (index == options.lastIndex) 16.dp else 0.dp,
                    bottomEnd = if (index == options.lastIndex) 16.dp else 0.dp
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = if (selectedButton == index) Color(0xFF00CCFF) else Color(0xFFE5E5E5)
                )
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {

                    // Only show the icon if the iconResIds list is not null and the index exists in the list
                    if (iconResIds != null && iconResIds.size > index) {
                        val iconResId = iconResIds[index]
                        val painter = painterResource(id = iconResId)

                        // Apply color filter to the PNG icon
                        Image(
                            painter = painter,
                            contentDescription = "Icon",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }


                    // Display the option text
                    Text(
                        text = option,
                        color = if (selectedButton == index) Color(0xFF00CCFF) else Color(0xFF4B4B4B),
                        style = TextStyle(fontSize = 15.sp, fontFamily = InterFontFamily),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Display description text
                    if (descriptions != null && descriptions.size > index){
                    Text(
                        text = descriptions[index],
                        color = if (selectedButton == index) Color(0xFF00CCFF) else Color(0xFF4B4B4B),
                        style = TextStyle(fontSize = 15.sp, fontFamily = InterFontFamily),
                        fontWeight = if (selectedButton == index) FontWeight.Medium else FontWeight.Light
                    )
                }}
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SetUpButtonPreview() {
    FieldWiseTheme {
        val options = listOf("5 min / day", "10 min / day", "15 min / day", "20 min / day")
        val descriptions = listOf("Light", "Moderate", "Serious", "Intense")
        val iconResIds = listOf(
            R.drawable.computer,
            R.drawable.computer,
            R.drawable.computer,
            R.drawable.computer
        )

        SetUpButton(options = options, descriptions = descriptions, iconResIds = iconResIds)
    }
}
