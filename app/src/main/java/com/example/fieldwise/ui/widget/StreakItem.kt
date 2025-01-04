import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.InterFontFamily
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.infiniteRepeatable

@Composable
fun StreakItem(
    modifier: Modifier = Modifier,
    resourceId: Int = R.drawable.streak, // Default to streak drawable
    steak: Int
) {
    val boxSize = remember { mutableStateOf(Size.Zero) }
    val streakAn = remember { Animatable(1f) }
    // animation
    LaunchedEffect(Unit) {
        streakAn.animateTo(
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                durationMillis = 800,
                delayMillis = 0
            ), repeatMode = RepeatMode.Reverse
            )
        )
        streakAn.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                durationMillis = 800,
                delayMillis = 0
            ), repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                boxSize.value = coordinates.size.toSize() // Capture the size of the Box
            },
        contentAlignment = Alignment.Center // Center content within the Box
    ) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = "streak item",
            modifier = Modifier
                .graphicsLayer {
                    scaleX = streakAn.value
                    scaleY = streakAn.value
                }
        )
        Text(
            text = steak.toString(), // Convert steak to string
            color = Color.White,
            fontSize = (boxSize.value.width * 0.15).sp, // Correctly access width for font size
            fontWeight = FontWeight.Bold,
            fontFamily = InterFontFamily,
            modifier = Modifier.offset(y = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StreakItemPreview() {
    FieldWiseTheme {
        StreakItem(
            modifier = Modifier.size(100.dp), // Set the size here
            steak = 1
        )
    }
}