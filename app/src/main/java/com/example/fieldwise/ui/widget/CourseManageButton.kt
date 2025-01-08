import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.fieldwise.R
import com.example.fieldwise.core.DatabaseProvider
import com.example.fieldwise.ui.screen.profile_creation.globalCourse
import com.example.fieldwise.ui.screen.profile_creation.globalLanguage
import com.example.fieldwise.ui.screen.profile_creation.globalUsername
import com.example.fieldwise.ui.theme.FieldWiseTheme
import com.example.fieldwise.ui.theme.SeravekFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CourseManageButton(
    modifier: Modifier = Modifier,
    NavigateToAddCourse: () -> Unit,
    NavigateToAddLanguage: () -> Unit,
    NavigateToLoadingScreen2: () -> Unit,
) {
    val context = LocalContext.current
    val userRepository = DatabaseProvider.provideUserRepository(context)
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val langFields = remember {
        mapOf(
            R.drawable.eng_rectangle to listOf(R.drawable.map to "Geography", R.drawable.computer to "Computer"),
            R.drawable.thai_rectangle to listOf(R.drawable.map to "Geography", R.drawable.computer to "Computer")
        )
    }

    // Load global language and course on first composition
    LaunchedEffect(Unit) {
        if (globalLanguage.isEmpty()) {
            globalLanguage = userRepository.getSavedLanguage() ?: ""
        }
        if (globalCourse.isEmpty()) {
            globalCourse = userRepository.getSavedCourse() ?: ""
        }
    }

    var selectedLang by remember {
        mutableStateOf(
            if (globalLanguage == "Thai") R.drawable.thai_rectangle else R.drawable.eng_rectangle
        )
    }

    var selectedField by remember {
        mutableStateOf(
            if (globalCourse == "Geography") R.drawable.map else R.drawable.computer
        )
    }

    // Animation for button click
    val buttonAn = remember { Animatable(1f) }

    fun saveGlobalStateAndNavigate() {
        coroutineScope.launch {
            try {
                userRepository.saveGlobal(globalUsername, globalLanguage, globalCourse)
                NavigateToLoadingScreen2()
            } catch (e: Exception) {
                println("Error updating profile: ${e.message}")
            }
        }
    }

    Box(modifier = modifier) {
        Button(
            onClick = {
                coroutineScope.launch {
                    buttonAn.animateTo(0.8f, animationSpec = tween(150, easing = FastOutSlowInEasing))
                    buttonAn.animateTo(1f, animationSpec = tween(150, easing = FastOutSlowInEasing))
                }
                expanded = true
            },
            modifier = Modifier
                .defaultMinSize(minWidth = 95.dp, minHeight = 33.dp)
                .graphicsLayer {
                    scaleX = buttonAn.value
                    scaleY = buttonAn.value
                }
                .testTag("CourseManageButton"),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = selectedLang),
                contentDescription = "Language",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = selectedField),
                contentDescription = "Field",
                modifier = Modifier.size(30.dp)
            )
        }

        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(0, 160),
                onDismissRequest = { expanded = false }
            ) {
                CourseManageDropdown(
                    onDismissRequest = { expanded = false },
                    onLangSelected = { lang ->
                        selectedLang = lang
                        selectedField = langFields[lang]?.firstOrNull()?.first ?: selectedField
                        globalLanguage = if (selectedLang == R.drawable.eng_rectangle) "English" else "Thai"
                        saveGlobalStateAndNavigate()
                    },
                    onFieldSelected = { field ->
                        selectedField = field
                        globalCourse = if (selectedField == R.drawable.computer) "Computer Science" else "Geography"
                        saveGlobalStateAndNavigate()
                    },
                    langs = listOf(
                        R.drawable.eng_rectangle to "English",
                        R.drawable.thai_rectangle to "Thai"
                    ),
                    fields = langFields[selectedLang] ?: emptyList(),
                    selectedLang = selectedLang,
                    selectedField = selectedField,
                    NavigateToAddCourse = NavigateToAddCourse,
                    NavigateToAddLanguage = NavigateToAddLanguage
                )
            }
        }
    }
}


@Composable
fun CourseManageDropdown(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onLangSelected: (Int) -> Unit,
    onFieldSelected: (Int) -> Unit,
    langs: List<Pair<Int, String>>,
    fields: List<Pair<Int, String>>,
    selectedLang: Int,
    selectedField: Int,
    NavigateToAddCourse: () -> Unit,
    NavigateToAddLanguage: () -> Unit
) {


    Column(
        modifier = Modifier
            .width(350.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Text("Languages", fontSize = 15.sp, fontFamily = SeravekFontFamily, fontWeight = FontWeight.Medium, color = Color(0xFF828282))
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            langs.forEach { lang ->
                val isSelected = lang.first == selectedLang
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onLangSelected(lang.first) }
                ) {
                    Image(painter = painterResource(id = lang.first), contentDescription = lang.second, modifier = Modifier.size(60.dp))
                    Text(
                        lang.second,
                        fontSize = 15.sp,
                        fontFamily = SeravekFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color(0xFF1AB8E8) else Color(0xFF828282)
                    )
                }
            }
            AddLanguageButton("Add language", NavigateToAddLanguage = NavigateToAddLanguage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Fields", fontSize = 15.sp, fontFamily = SeravekFontFamily, fontWeight = FontWeight.Medium, color = Color(0xFF828282))
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            fields.forEach { field ->
                val isSelected = field.first == selectedField
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onFieldSelected(field.first) }
                ) {
                    Image(painter = painterResource(id = field.first), contentDescription = field.second, modifier = Modifier.size(60.dp))
                    Text(
                        field.second,
                        fontSize = 15.sp,
                        fontFamily = SeravekFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color(0xFF1AB8E8) else Color(0xFF828282)
                    )
                }
            }
            AddCourseButton("add fields", NavigateToAddCourse = NavigateToAddCourse)
        }
    }
}

@Composable
fun AddLanguageButton(description: String, NavigateToAddLanguage: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { NavigateToAddLanguage() }.testTag("AddLanguage")
    ) {
        Image(painter = painterResource(id = R.drawable.add), contentDescription = description, modifier = Modifier.size(60.dp))
        Text("Add", fontSize = 15.sp, fontFamily = SeravekFontFamily, fontWeight = FontWeight.Medium, color = Color(0xFFC5C5C5))
    }
}

@Composable
fun AddCourseButton(description: String, NavigateToAddCourse: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { NavigateToAddCourse() }.testTag("AddField")
    ) {
        Image(painter = painterResource(id = R.drawable.add), contentDescription = description, modifier = Modifier.size(60.dp))
        Text("Add", fontSize = 15.sp, fontFamily = SeravekFontFamily, fontWeight = FontWeight.Medium, color = Color(0xFFC5C5C5))
    }
}


@Preview(showBackground = true)
@Composable
fun CourseManageButtonPreview() {
    FieldWiseTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CourseManageButton(
                modifier = Modifier.align(Alignment.TopStart),
                NavigateToAddCourse = {},
                NavigateToAddLanguage = {},
                NavigateToLoadingScreen2 = {}
            )
        }
    }
}