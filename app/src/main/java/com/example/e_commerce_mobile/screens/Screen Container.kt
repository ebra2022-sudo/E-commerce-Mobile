package com.example.e_commerce_mobile.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_mobile.screens.user_account_management.LogInScreen
import com.example.e_commerce_mobile.screens.user_account_management.PasswordResetScreen
import com.example.e_commerce_mobile.screens.user_account_management.SignUpScreen

@Composable
fun ScreenContainer(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(startDestination = Screens.LogInScreen.route, navController = navController) {
        composable(Screens.LogInScreen.route) {
            LogInScreen(navController = navController)
        }
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screens.PasswordResetScreen.route) {
            PasswordResetScreen(navController = navController)
        }
    }
}