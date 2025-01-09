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
import com.example.fieldwise.ui.screen.profile_creation.preferredLanguage
import com.example.fieldwise.ui.screen.profile_creation.selectedCourse
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


    // Load global language and course on first composition
    LaunchedEffect(Unit) {
        if (preferredLanguage.isEmpty()) {
            preferredLanguage = userRepository.getSavedLanguage() ?: ""
        }
        if (selectedCourse.isEmpty()) {
            selectedCourse = userRepository.getSavedCourse() ?: ""
        }
    }

    var selectedLang by remember {
        mutableStateOf(
            if (preferredLanguage == "Thai") R.drawable.thai_rectangle else R.drawable.eng_rectangle
        )
    }

    var selectedField by remember {
        mutableStateOf(
            if (selectedCourse == "Geography") R.drawable.map else R.drawable.computer
        )
    }

    // States for user languages and fields
    var userLanguages by remember { mutableStateOf<List<String>>(emptyList()) }
    var userFields by remember { mutableStateOf<List<String>>(emptyList()) }

    // Load languages and fields
    LaunchedEffect(globalUsername) {
        // Fetch user languages and fields asynchronously
        try {
            userLanguages = userRepository.getUserLanguages(globalUsername) ?: emptyList()
            userFields = userRepository.getUserFields(globalUsername) ?: emptyList()
        } catch (e: Exception) {
            println("Error fetching user languages/fields: ${e.message}")
        }
    }

    // Animation for button click
    val buttonAn = remember { Animatable(1f) }

    fun saveGlobalStateAndNavigate() {
        coroutineScope.launch {
            try {
                userRepository.saveGlobal(globalUsername, preferredLanguage, selectedCourse)
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
                val context = LocalContext.current
                val userRepository = DatabaseProvider.provideUserRepository(context)
                CourseManageDropdown(
                    onDismissRequest = { expanded = false },
                    onLangSelected = { lang ->
                        selectedLang = lang
                        preferredLanguage = if (selectedLang == R.drawable.eng_rectangle) "English" else "Thai"

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val userProfile = userRepository.getUserProfile(globalUsername)
                                if (userProfile != null) {
                                    val updatedLanguages = userProfile.languages.toMutableList().apply {
                                        if (!contains(preferredLanguage)) add(preferredLanguage)
                                    }
                                    userRepository.updateUserProfile(
                                        userProfile.copy(
                                            preferredLanguage = preferredLanguage,
                                            languages = updatedLanguages
                                        )
                                    )
                                }
                            } catch (e: Exception) {
                                println("Error updating preferred language: ${e.message}")
                            }
                        }
                        saveGlobalStateAndNavigate()
                    },
                    onFieldSelected = { field ->
                        selectedField = field
                        selectedCourse = if (selectedField == R.drawable.map) "Geography" else "Computer Science"

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val userProfile = userRepository.getUserProfile(globalUsername)
                                if (userProfile != null) {
                                    val updatedCourses = userProfile.courses.toMutableList().apply {
                                        if (!contains(selectedCourse)) add(selectedCourse)
                                    }
                                    userRepository.updateUserProfile(
                                        userProfile.copy(
                                            selectedCourse = selectedCourse,
                                            courses = updatedCourses
                                        )
                                    )
                                }
                            } catch (e: Exception) {
                                println("Error updating selected course: ${e.message}")
                            }
                        }
                        saveGlobalStateAndNavigate()
                    },
                    langs = userLanguages,
                    fields = userFields,
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
    langs: List<String>?,
    fields: List<String>?,
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
        // Language Selection
        Text(
            text = "Languages",
            fontSize = 15.sp,
            fontFamily = SeravekFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF828282)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            langs?.forEachIndexed { index, lang ->
                val resIdLang = if (lang == "Thai") R.drawable.thai_rectangle else R.drawable.eng_rectangle
                val isSelectedLang = selectedLang == resIdLang
                Log.d("selectedLang == resIdLang", isSelectedLang.toString())

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = if (!isSelectedLang) Modifier.clickable { onLangSelected(resIdLang) } else Modifier
                ) {
                    Image(
                        painter = painterResource(id = resIdLang),
                        contentDescription = lang,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = lang,
                        fontSize = 15.sp,
                        fontFamily = SeravekFontFamily,
                        fontWeight = if (isSelectedLang) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelectedLang) Color(0xFF1AB8E8) else Color(0xFF828282)
                    )
                }
            }
            AddLanguageButton("Add language", NavigateToAddLanguage = NavigateToAddLanguage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Field Selection
        Text(
            text = "Fields",
            fontSize = 15.sp,
            fontFamily = SeravekFontFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF828282)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            fields?.forEachIndexed { index, field ->
                val resIdLField = if (field == "Geography") R.drawable.map else R.drawable.computer
                val isSelectedField = selectedField == resIdLField
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = if (!isSelectedField) Modifier.clickable { onLangSelected(resIdLField) } else Modifier
                ) {
                    Image(
                        painter = painterResource(id = resIdLField),
                        contentDescription = field,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = field,
                        fontSize = 15.sp,
                        fontFamily = SeravekFontFamily,
                        fontWeight = if (isSelectedField) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelectedField) Color(0xFF1AB8E8) else Color(0xFF828282)
                    )
                }
            }
            AddCourseButton("Add fields", NavigateToAddCourse = NavigateToAddCourse)
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