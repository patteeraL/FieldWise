import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme

@Composable
fun CourseManageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    lang: Int,
    field: Int
) {
    Button(
        onClick = onClick,
        modifier = Modifier.defaultMinSize(
            minWidth = 95.dp,
            minHeight = 33.dp
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = lang),
            contentDescription = "Language",
            modifier = modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(id = field),
            contentDescription = "Field",
            modifier = modifier.size(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CourseManageButtonPreview() {
    FieldWiseTheme {
        CourseManageButton(
            modifier = Modifier,
            onClick = { },
            lang = R.drawable.spain_rectangle,
            field = R.drawable.computer
        )
    }
}