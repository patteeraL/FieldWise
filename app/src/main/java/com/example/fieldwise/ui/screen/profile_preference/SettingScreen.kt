package com.example.fieldwise.ui.screen.profile_preference

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import com.example.fieldwise.ui.widget.GoBackButton
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType

@Composable
fun SettingScreen(modifier: Modifier = Modifier, NavigateToProfile: () -> Unit)  {

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
            ) {
                Box {
                    GoBackButton( onClick = { NavigateToProfile() })
                }
                Text(
                    text = "Setting",
                    modifier = modifier
                        .fillMaxWidth()
                        .offset(x = (-20).dp),
                    fontSize = 29.sp,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )


            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(modifier = modifier.fillMaxWidth() ,thickness = 2.dp, color = Color(0xFFCCCCCC))
        Column(modifier = modifier
            .fillMaxSize()) {
            DropDownDemo()
        }
    }

}


@Composable
fun DropDownDemo() {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val dailygoals = listOf("5 min / day (Light)", "10 min / day (Light)", "15 min / day (Light)", "20 min / day (Light)")

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Daily Goal",
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            fontSize = 19.sp,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Box {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(346.dp)
                    .height(60.dp)
                    .background(color = Color.White)
                    .border(
                        width = 2.5.dp,
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .clickable {
                        isDropDownExpanded.value = true
                    }
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = dailygoals[itemPosition.value],
                        fontSize = 18.sp,
                        fontFamily = InterFontFamily
                    )
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            DropdownMenu(
                modifier = Modifier.width(346.dp).background(color = Color.White),
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                },) {
                dailygoals.forEachIndexed { index, dailygoal ->
                    DropdownMenuItem(
                        text = {
                        Text(text = dailygoal)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                        })
                }
            }

        }
        Spacer(modifier = Modifier.height(40.dp)) //qué queremos que haga al presionar los botones?
        MainButton(button = "SAVE CHANGES", onClick = {/* Do Something */}, mainButtonType = MainButtonType.BLUE)
        Spacer(modifier = Modifier.height(25.dp))
        MainButton(button = "DELETE COURSE", onClick = {/* Do Something */}, mainButtonType = MainButtonType.RED)
        Spacer(modifier = Modifier.height(25.dp))
        MainButton(button = "DELETE ACCOUNT", onClick = {/* Do Something */}, mainButtonType = MainButtonType.NOCOLOR)

    }}



@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    FieldWiseTheme {
        SettingScreen(
            NavigateToProfile = {}
        )
    }
}
