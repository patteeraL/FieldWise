package com.example.fieldwise.navigation


import CourseManageButton
import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.ui.screen.course_manage.AddDailyGoalScreen
import com.example.fieldwise.ui.screen.course_manage.AddFieldScreen
import com.example.fieldwise.ui.screen.course_manage.AddLanguageScreen
import com.example.fieldwise.ui.screen.home_page.HomeScreen
import com.example.fieldwise.ui.screen.home_page.LoadingScreen2
import com.example.fieldwise.ui.screen.leaderboard.LeaderBoardScreen
import com.example.fieldwise.ui.screen.lessons.ExerciseCompleteScreen
import com.example.fieldwise.ui.screen.home_page.LoadingScreen3
import com.example.fieldwise.ui.screen.lessons.SelectExerciseScreen
import com.example.fieldwise.ui.screen.lessons.conversation.ConversationScreen1
import com.example.fieldwise.ui.screen.lessons.listening.ListeningScreen1
import com.example.fieldwise.ui.screen.lessons.listening.ListeningScreen2
import com.example.fieldwise.ui.screen.lessons.speaking.SpeakingScreen1
import com.example.fieldwise.ui.screen.lessons.vocabulary.VocabScreen1
import com.example.fieldwise.ui.screen.lessons.vocabulary.VocabScreen2
import com.example.fieldwise.ui.screen.profile_creation.CompleteScreen
import com.example.fieldwise.ui.screen.profile_creation.CourseManageScreen
import com.example.fieldwise.ui.screen.profile_creation.EnableNotifyScreen
import com.example.fieldwise.ui.screen.profile_creation.LoadingScreen
import com.example.fieldwise.ui.screen.profile_creation.SetDailyGoalScreen
import com.example.fieldwise.ui.screen.profile_creation.UsernameScreen
import com.example.fieldwise.ui.screen.profile_preference.AddFriendScreen
import com.example.fieldwise.ui.screen.profile_preference.ProfileScreen
import com.example.fieldwise.ui.screen.profile_preference.SettingScreen
import com.example.fieldwise.ui.widget.CardType
import com.example.fieldwise.ui.widget.LessonCard

// Define Routes as constants
object Routes {
    const val Splash = "splash"
    const val UserName = "username"
    const val Loading = "loading"
    const val Loading2 = "loading2"
    const val Loading3 = "loading3"
    const val DailyGoal = "daily goal"
    const val Notify = "notify"
    const val Course = "course"
    const val Complete = "complete"
    const val Home = "home"
    const val LeaderBoard = "leader_board/{type}"
    const val AddDailyGoal = "add_daily_goal/{type}"
    const val CourseManage = "course manage"
    const val AddField = "add field"
    const val AddLanguage = "add language"
    const val ProfileScreen = "profile screen"
    const val SettingScreen = "setting screen"
    const val AddFriend = "add friend"
    const val Lesson = "lesson"
    const val SelectExercise = "select exercise"
    const val SpeakingScreen = "speaking screen"
    const val ListeningScreen1 = "listening screen 1"
    const val ListeningScreen2 = "listening screen 2"
    const val ConversationScreen = "conversation screen"
    const val VocabularyScreen1 = "vocabulary screen 1"
    const val VocabularyScreen2 = "vocabulary screen 2"
    const val ExerciseComplete = "exercise complete"
}

@Composable
fun NavigationWrapper(isFirstTime: Boolean) {
    val navController = rememberNavController()
    var isFirstTime = isFirstTime

    NavHost(
        navController = navController,
        startDestination = if (isFirstTime == true) Routes.Splash else Routes.Loading3
    ) {
        // Begin in SplashScreen
        if(isFirstTime == true) {
            composable(Routes.Splash) {
                SplashScreen {
                    navController.navigate(Routes.Loading) // Navigate to UserNameScreen
                }
            }

            composable(Routes.Loading) {
                LoadingScreen {
                    navController.navigate(Routes.UserName)
                }
            }

            // UserNameScreen
            composable(Routes.UserName) {
                UsernameScreen {
                    navController.navigate(Routes.DailyGoal)
                }
            }

            composable(Routes.DailyGoal) {
                SetDailyGoalScreen(
                    NavigateToNotification = { navController.navigate(Routes.Notify) },
                    NavigateToUserName = { navController.navigate(Routes.UserName) }

                )
            }

            composable(Routes.Notify) { //add more when new buttom to go back
                EnableNotifyScreen(
                    NavigateToCourse = { navController.navigate(Routes.Course) },
                    NavigateToGoal = { navController.navigate(Routes.DailyGoal) }
                )
            }

            composable(Routes.Course) {
                CourseManageScreen(
                    NavigateToComplete = { navController.navigate(Routes.Complete) },
                    NavigateToNotification = { navController.navigate(Routes.Notify) }
                )
            }

            composable(Routes.Complete) {
                CompleteScreen { navController.navigate(Routes.Home) }
            }
        }

        composable(Routes.Loading3) {
            LoadingScreen3 {
                navController.navigate(Routes.Home)
            }
        }

        composable(Routes.Home) {
            HomeScreen(
                NavigateToLeader = {navController.navigate("${Routes.LeaderBoard}/home")},
                NavigateToAddLanguage = {navController.navigate("${Routes.AddDailyGoal}/language")}, //go the same screen but it depends on the button you have clicked
                NavigateToAddCourse = {navController.navigate("${Routes.AddDailyGoal}/course")}, //here i define the value of "type"
                NavigateToLoadingScreen2 = {navController.navigate(Routes.Loading2)},
                NavigateToProfile = {navController.navigate(Routes.ProfileScreen)},
                NavigateToLessons = {navController.navigate(Routes.SelectExercise)},
                NavigateToQuiz = {navController.navigate("${Routes.ListeningScreen1}/quiz")}
                )
        }

        composable("${Routes.LeaderBoard}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            LeaderBoardScreen(
                type = type,
                NavigateBack = {
                    if (type == "home") { navController.navigate(Routes.Home)
                    } else if (type == "profile") { navController.navigate(Routes.ProfileScreen)}
                    else if (type == "exercise") { navController.navigate(Routes.SelectExercise)}
                }
            )
        }

        composable(Routes.Loading2) {
            LoadingScreen2 {
                navController.navigate(Routes.Home)
            }
        }

        composable("${Routes.AddDailyGoal}/{type}"){ backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            AddDailyGoalScreen(
                type = type,
                MainButtonClick = {
                    when (type) { //Go to a different screen depending on the type value
                        "language" -> navController.navigate(Routes.AddLanguage)
                        "course" -> navController.navigate(Routes.AddField)
                    }
                },
                NavigateToHome = {navController.navigate(Routes.Home)}
            )
        }



        composable(Routes.CourseManage) {
            CourseManageButton(
                NavigateToAddCourse = {navController.navigate("${Routes.AddDailyGoal}/course")},
                NavigateToAddLanguage = {navController.navigate("${Routes.AddDailyGoal}/language")},
                NavigateToLoadingScreen2 = {navController.navigate(Routes.Loading2)}
            )
        }

        composable(Routes.AddField) {
            AddFieldScreen(
                NavigateToDailyGoal = {navController.navigate("${Routes.AddDailyGoal}/course")},
                NavigateToComplete = {navController.navigate(Routes.Complete)}

            )
        }

        composable(Routes.AddLanguage) {
            AddLanguageScreen(
                NavigateToComplete = {navController.navigate(Routes.Complete)},
                NavigateToDailyGoal = {navController.navigate("${Routes.AddDailyGoal}/language")} //contains type's value (example: it come back to AddDailyGoal/language
            )
        }

        composable(Routes.ProfileScreen) {
            ProfileScreen(
                NavigateToHome = {navController.navigate(Routes.Home)},
                NavigateToSettings = {navController.navigate(Routes.SettingScreen)},
                NavigateToLeader = {navController.navigate("${Routes.LeaderBoard}/profile")},
                NavigateToAddFriend = {navController.navigate(Routes.AddFriend)}
            )
        }

        composable(Routes.SettingScreen) {
            SettingScreen(
                NavigateToProfile = {navController.navigate(Routes.ProfileScreen)},
                Restart = {navController.navigate(Routes.Splash)}
            )
        }

        composable(Routes.AddFriend) {
            AddFriendScreen(
                NavigateToProfile = {navController.navigate(Routes.ProfileScreen)}
            )
        }

        composable(Routes.Lesson) {
            LessonCard(
                NavigateToLessons = { navController.navigate(Routes.SelectExercise) },
                NavigateToQuiz = {navController.navigate("${Routes.ListeningScreen1}/quiz")},
                title = "Lesson 1", description = "Consumer and Producer Behavior",cardType = CardType.BLUE, progress = 1f, complete = true
            )
        }

        composable(Routes.SelectExercise) {
            SelectExerciseScreen(
                NavigateToLeader = {navController.navigate("${Routes.LeaderBoard}/exercise")},
                NavigateToHome = {navController.navigate(Routes.Home)},
                NavigateToListening = {navController.navigate("${Routes.ListeningScreen1}/exercise")},
                NavigateToConversation = {navController.navigate("${Routes.ConversationScreen}/exercise")},
                NavigateToSpeaking = {navController.navigate("${Routes.SpeakingScreen}/exercise")},
                NavigateToVocabulary = {navController.navigate("${Routes.VocabularyScreen1}/exercise")},
                OpenTest = {navController.navigate("${Routes.ListeningScreen1}/quiz")}

            )

        }

        composable("${Routes.ListeningScreen1}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
           ListeningScreen1 (
               type = type,
               NextExercise = {
                   if (type == "exercise") {navController.navigate("${Routes.ListeningScreen2}/exercise")}
                   else if (type == "quiz") {navController.navigate("${Routes.ListeningScreen2}/quiz")}
               },
               ExitLesson = {
                   if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                else if (type == "quiz") {navController.navigate(Routes.Home)}}
           )

       }

        composable("${Routes.ListeningScreen2}/{type}") { backStackEntry ->
        var type = backStackEntry.arguments?.getString("type")
            ListeningScreen2(
                type = type,
                NextExercise = {
                    if (type == "exercise") {navController.navigate("${Routes.ExerciseComplete}/exercise")}
                    else if (type == "quiz") {navController.navigate("${Routes.SpeakingScreen}/quiz")}
                },
                ExitLesson = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}}
            )
        }

        composable("${Routes.SpeakingScreen}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            SpeakingScreen1(
                type = type,
                NextExercise = {
                    if (type == "exercise") {navController.navigate("${Routes.ExerciseComplete}/exercise")}
                    else if (type == "quiz") {navController.navigate("${Routes.ConversationScreen}/quiz")}},
                ExitLesson = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}}
            )
        }

        composable("${Routes.ConversationScreen}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            ConversationScreen1(
                type = type,
                NextExercise = {
                    if (type == "exercise") {navController.navigate("${Routes.ExerciseComplete}/exercise")}
                    else if (type == "quiz") {navController.navigate("${Routes.VocabularyScreen1}/quiz")}
                },
                ExitLesson = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}}
            )
        }

        composable("${Routes.VocabularyScreen1}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            VocabScreen1(
                type = type,
                NextExercise = {
                    if (type == "exercise") {navController.navigate("${Routes.VocabularyScreen2}/exercise")}
                    else if (type == "quiz") {navController.navigate("${Routes.VocabularyScreen2}/quiz")}
                },
                ExitLesson = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}}

            )
        }

        composable("${Routes.VocabularyScreen2}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            VocabScreen2(
                type = type,
                NextExercise = {
                    if (type == "exercise") {navController.navigate("${Routes.ExerciseComplete}/exercise")}
                    else if (type == "quiz") {navController.navigate("${Routes.ExerciseComplete}/quiz")}
                },
                ExitLesson = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}}
            )
        }

        composable("${Routes.ExerciseComplete}/{type}") { backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            ExerciseCompleteScreen(
                type = type ?: "default",
                Finish = {
                    if (type == "exercise") {navController.navigate(Routes.SelectExercise)}
                    else if (type == "quiz") {navController.navigate(Routes.Home)}
                }

            )

        }


        }


    }

