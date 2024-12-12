package com.example.fieldwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.fieldwise.navigation.NavigationWrapper
import com.example.fieldwise.ui.screen.profile_creation.UsernameScreen
import com.example.fieldwise.ui.theme.FieldWiseTheme


val InterFontFamily = FontFamily(
    Font(R.font.inter_18pt_bold, FontWeight.Bold),
    Font(R.font.inter_18pt_medium, FontWeight.Medium),
    Font(R.font.inter_18pt_light, FontWeight.Light)

)

val ShantellSansFontFamily = FontFamily(
    Font(R.font.shantellsans_extrabold, FontWeight.ExtraBold)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationGuideTheme {
                NavigationWrapper()

            }
            FieldWiseTheme {

                UsernameScreen()
            }
        }
    }


}

@Composable
fun NavigationGuideTheme(function: @Composable () -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FieldWiseTheme {

    }
}



