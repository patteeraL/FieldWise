import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.fieldwise.R
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Discussion(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header Section
        Box(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF073748))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.discussion),
                    contentDescription = "Light Bulb Icon",
                    modifier = Modifier.size(24.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Text(
                            text = "Discussion",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                fontFamily = SeravekFontFamily
                            )
                        )
                    }
                    IconButton(
                        onClick = {
                            isExpanded = !isExpanded
                        },
                        modifier = modifier.offset(20.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // LazyColumn Section - Appears only when expanded
        if (isExpanded) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF073748)) // Match the background color
        ) {
            // Comment input box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    placeholder = {
                        Text(
                            text = "Type a comment...",
                            color = Color(0xFF0B4D65)
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontFamily = SeravekFontFamily,
                        letterSpacing = 1.sp
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF092028)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                Button(
                    onClick = {
                        if (comment.isNotBlank()) {
                            comments = comments + comment
                            comment = ""
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .padding(top = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CCFF))
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.sendicon),
                        contentDescription = null
                    )
                }
            }

            // Spacer between sections
            Spacer(modifier = Modifier.height(10.dp))

            // Message card
            Box(
                modifier = Modifier
                    .border(
                        width = 1.5.dp,
                        color = Color(0xFF62AAC2),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .fillMaxWidth()
                    .height(83.dp)
                    .background(
                        color = Color(0xFF24586A),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lightbulbicon),
                        contentDescription = "Light Bulb Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Discussions should focus on providing feedback, tips, or asking questions related to this topic.",
                            color = Color.White,
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light, fontFamily = SeravekFontFamily),
                        )
                        Text(
                            text = "Sharing solutions here is not allowed.",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = SeravekFontFamily),
                        )
                    }
                }
            }

            // Spacer between sections
            Spacer(modifier = Modifier.height(20.dp))

            // Displaying the list of comments
            comments.forEachIndexed { index, comment ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(70.dp)
                        .background(color = Color(0xFF0B4D65), shape = RoundedCornerShape(size = 15.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.profile), // Replace with your user icon resource
                                contentDescription = "User Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Thee",
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = SeravekFontFamily)
                            )
                        }
                        Text(
                            text = comment,
                            color = Color.White,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light, fontFamily = SeravekFontFamily),
                            modifier = Modifier.padding(start = 30.dp)
                        )
                    }
                }
            }
        }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DiscussionPreview() {
    FieldWiseTheme {
        Discussion()
    }
}