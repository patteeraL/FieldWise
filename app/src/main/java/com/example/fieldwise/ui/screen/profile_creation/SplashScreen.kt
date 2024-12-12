import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.R
import com.example.fieldwise.ShantellSansFontFamily
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.MainButton

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navigateToHome: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashIcon()
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between icon and button
        Text( text = "FieldWise",
            color = Color(0xFF00CCFF),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ShantellSansFontFamily
            ))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigateToHome() }) {
            Text(text = "GET STARTED") //change the format to be able to navigate
        }// Renamed for clarity button = "GET STARTED"
    }
}



@Composable
fun SplashIcon(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.robot_w_bg),
        contentDescription = "Splash icon",
        modifier = modifier
            .size(250.dp) // Set a default size
    )
}



@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    FieldWiseTheme {
        SplashScreen {  }
    }
}
