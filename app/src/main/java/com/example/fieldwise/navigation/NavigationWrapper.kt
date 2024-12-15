package com.example.fieldwise.navigation

import SplashScreen
import android.app.Notification
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.ui.screen.profile_creation.UsernameScreen
import com.example.fieldwise.ui.screen.profile_creation.LoadingScreen
import com.example.fieldwise.ui.screen.profile_creation.SetDailyGoalScreen
import com.example.fieldwise.ui.screen.profile_creation.EnableNotifyScreen
import com.example.fieldwise.ui.screen.profile_creation.CourseManageScreen
import com.example.fieldwise.ui.screen.profile_creation.CompleteScreen

// Define Routes as constants
object Routes {
    const val Splash = "splash"
    const val UserName = "username"
    const val Loading = "loading"
    const val DailyGoal = "daily goal"
    const val Notify = "notify"
    const val Course = "course"
    const val Complete = "complete"
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
            SetDailyGoalScreen{
                navController.navigate(Routes.Notify)
            }
        }

        composable(Routes.Notify) { //add more when new buttom to go back
            EnableNotifyScreen{
                navController.navigate(Routes.Course)
            }
        }

        composable(Routes.Course) {
            CourseManageScreen{
                navController.navigate(Routes.Complete)
            }
        }

        composable(Routes.Complete) {
            CompleteScreen()
        }


    }
}
