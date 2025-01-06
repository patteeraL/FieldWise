import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.ShantellSansFontFamily
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.widget.MainButton
import com.example.fieldwise.ui.widget.MainButtonType
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.ui.platform.testTag


@Composable
fun SplashScreen(navigateToLoading: () -> Unit) {

    //bounce animation for text
    val textAn = remember { Animatable(0f)}

    LaunchedEffect(Unit) {
        textAn.animateTo(
            targetValue = 20f,
            animationSpec =  infiniteRepeatable(
                animation =  tween(
                    durationMillis = 1000,
                    easing = { OvershootInterpolator(4f).getInterpolation(it) }

                ),
                repeatMode = RepeatMode.Reverse

            )
        )
    }
    Column(
        modifier = Modifier.fillMaxSize().testTag("SplashScreen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashIcon()
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between icon and button
        Text( text = "FieldWise",
            color = Color(0xFF00CCFF),
            modifier = Modifier.offset(y = textAn.value.dp),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ShantellSansFontFamily,
            ))
        Spacer(modifier = Modifier.height(80.dp))
        MainButton(
            onClick = { navigateToLoading() },
            mainButtonType = MainButtonType.BLUE,
            button = "GET STARTED",
            isEnable = true
        )
        //change the format to be able to navigate
    }// Renamed for clarity button = "GET STARTED"
}




@Composable
fun SplashIcon(modifier: Modifier = Modifier){

    //bounce animation for image
    val splashAn = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        splashAn.animateTo(
            targetValue = 20f,
            animationSpec =  infiniteRepeatable(
                animation =  tween(
                    durationMillis = 1000,
                    easing = { OvershootInterpolator(4f).getInterpolation(it) }

                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Image(
        painter = painterResource(id = R.drawable.robot_w_bg),
        contentDescription = "Splash icon",
        modifier = modifier
            .size(250.dp) // Set a default size
            .offset(y = splashAn.value.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    FieldWiseTheme {
        SplashScreen {  }
    }
}
