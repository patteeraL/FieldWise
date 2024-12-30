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
import com.example.fieldwise.ui.screen.leaderboard.LeaderBoardScreen
import com.example.fieldwise.ui.screen.lessons.ExerciseCompleteScreen
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
import com.example.fieldwise.ui.widget.FriendSearchCard
import com.example.fieldwise.ui.widget.LessonCard
import kotlin.random.Random

// Define Routes as constants
object Routes {
    const val Splash = "splash"
    const val UserName = "username"
    const val Loading = "loading"
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
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        // Begin in SplashScreen
        composable(Routes.Splash) {
            SplashScreen { 
                navController.navigate(Routes.Loading) // Navigate to UserNameScreen
            }
        }

        composable(Routes.Loading) {
            LoadingScreen{
                navController.navigate(Routes.UserName)
            }
        }

        // UserNameScreen
        composable(Routes.UserName) {
            UsernameScreen{
                navController.navigate(Routes.DailyGoal)
            }
        }

        composable(Routes.DailyGoal) {
            SetDailyGoalScreen(
                NavigateToNotification = { navController.navigate(Routes.Notify) },
                NavigateToUserName = { navController.navigate(Routes.UserName)}

            )
        }

        composable(Routes.Notify) { //add more when new buttom to go back
            EnableNotifyScreen(
                NavigateToCourse = {navController.navigate(Routes.Course)},
                NavigateToGoal =  { navController.navigate(Routes.DailyGoal)}
                )
        }

        composable(Routes.Course) {
            CourseManageScreen(
                NavigateToComplete = { navController.navigate(Routes.Complete)},
                NavigateToNotification = {navController.navigate(Routes.Notify)}
            )
        }

        composable(Routes.Complete) {
            CompleteScreen{ navController.navigate(Routes.Home)}
        }

        composable(Routes.Home) {
            HomeScreen(
                NavigateToLeader = {navController.navigate("${Routes.LeaderBoard}/home")},
                NavigateToAddLanguage = {navController.navigate("${Routes.AddDailyGoal}/language")}, //go the same screen but it depends on the button you have clicked
                NavigateToAddCourse = {navController.navigate("${Routes.AddDailyGoal}/course")}, //here i define the value of "type"
                NavigateToProfile = {navController.navigate(Routes.ProfileScreen)},
                NavigateToLessons = {navController.navigate(Routes.SelectExercise)}
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


        composable("${Routes.AddDailyGoal}/{type}"){ backStackEntry ->
            var type = backStackEntry.arguments?.getString("type")
            AddDailyGoalScreen(
                type = type,
                MainButtonClick = { //Go to a different screen depending on the type value
                    if (type == "language") {
                        navController.navigate(Routes.AddLanguage)
                    } else if (type == "course") {
                        navController.navigate(Routes.AddField)
                    }
                },
                NavigateToHome = {navController.navigate(Routes.Home)}
            )
        }



        composable(Routes.CourseManage) {
            CourseManageButton(
                NavigateToAddCourse = {navController.navigate("${Routes.AddDailyGoal}/course")},
                NavigateToAddLanguage = {navController.navigate("${Routes.AddDailyGoal}/language")}
            )
        }

        composable(Routes.AddField) {
            AddFieldScreen(
                NavigateToDailyGoal = {navController.navigate("${Routes.AddDailyGoal}/{type}")},
                NavigateToComplete = {navController.navigate(Routes.Complete)}

            )
        }

        composable(Routes.AddLanguage) {
            AddLanguageScreen(
                NavigateToComplete = {navController.navigate(Routes.Complete)},
                NavigateToDailyGoal = {navController.navigate("${Routes.AddDailyGoal}/{type}")} //contains type's value (example: it come back to AddDailyGoal/language
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
                title = "Lesson 1", description = "Consumer and Producer Behavior",cardType = CardType.BLUE, progress = 1f, complete = true
            )
        }

        composable(Routes.SelectExercise) {
            SelectExerciseScreen(
                NavigateToLeader = {navController.navigate("${Routes.LeaderBoard}/exercise")},
                NavigateToHome = {navController.navigate(Routes.Home)},
                NavigateToListening = {navController.navigate(Routes.ListeningScreen1)},
                NavigateToConversation = {navController.navigate(Routes.ConversationScreen)},
                NavigateToSpeaking = {navController.navigate(Routes.SpeakingScreen)},
                NavigateToVocabulary = {navController.navigate(Routes.VocabularyScreen1)}
            )

        }

        composable(Routes.ListeningScreen1) {
           ListeningScreen1 (
               ExitLesson = {navController.navigate(Routes.SelectExercise)},
               NextExercise = {navController.navigate(Routes.ListeningScreen2)}
           )

       }

        composable(Routes.ListeningScreen2) {
            ListeningScreen2(
                ExitLesson = {navController.navigate(Routes.SelectExercise)},
                ExerciseComplete = {navController.navigate(Routes.ExerciseComplete)}
            )
        }

        composable(Routes.SpeakingScreen) {
            SpeakingScreen1(
                ExitLesson = {navController.navigate(Routes.SelectExercise)},
                ExerciseComplete = {navController.navigate(Routes.ExerciseComplete)}
            )
        }

        composable(Routes.ConversationScreen) {
            ConversationScreen1(
                ExerciseComplete = {navController.navigate(Routes.ExerciseComplete)},
                ExitLesson = {navController.navigate(Routes.SelectExercise)}
            )
        }

        composable(Routes.VocabularyScreen1) {
            VocabScreen1(
                ExitLesson = {navController.navigate(Routes.SelectExercise)},
                NextExercise = {navController.navigate(Routes.VocabularyScreen2)}

            )
        }

        composable(Routes.VocabularyScreen2) {
            VocabScreen2(
                ExitLesson = {navController.navigate(Routes.SelectExercise)},
                ExerciseComplete = {navController.navigate(Routes.ExerciseComplete)}
            )
        }

        composable(Routes.ExerciseComplete) {
            ExerciseCompleteScreen(
                NavigateToLesson = {navController.navigate(Routes.SelectExercise)}
            )

        }
        }


    }

