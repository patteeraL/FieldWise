package com.example.fieldwise.navigation

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldwise.ui.screen.profile_creation.UsernameScreen
import com.example.fieldwise.ui.screen.profile_creation.LoadingScreen

// Define las rutas como constantes
object Routes {
    const val Splash = "splash"
    const val UserName = "username"
    const val Loading = "loading"
}

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        // Pantalla de Splash
        composable(Routes.Splash) {
            SplashScreen { 
                navController.navigate(Routes.Loading) // Navegar a UsernameScreen
            }
        }

        composable(Routes.Loading) {
            LoadingScreen{
                navController.navigate(Routes.UserName)
            }
        }

        // Pantalla de Username
        composable(Routes.UserName) {
            UsernameScreen() // Aquí está la pantalla de nombre de usuario
        }
    }
}
