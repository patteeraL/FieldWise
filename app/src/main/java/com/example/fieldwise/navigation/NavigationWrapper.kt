package com.example.fieldwise.navigation

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.ui.screen.profile_creation.UsernameScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Initial) {
        composable<Initial>{
            SplashScreen { navController.navigate(UserName) }
        }

        composable<UserName>{
            UsernameScreen()
        }
    }
}