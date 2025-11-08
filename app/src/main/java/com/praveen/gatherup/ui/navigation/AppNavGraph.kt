package com.praveen.gatherup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.praveen.gatherup.ui.auth.LoginScreen
import com.praveen.gatherup.ui.auth.SignupScreen
import com.praveen.gatherup.ui.auth.OtpVerifyScreen
import com.praveen.gatherup.ui.auth.ProfileUploadScreen
import com.praveen.gatherup.ui.auth.ForgotPasswordScreen
import com.praveen.gatherup.ui.auth.NewPasswordScreen
import com.praveen.gatherup.ui.home.HomeScreen
import com.praveen.gatherup.viewmodel.AuthViewModel

@Composable
fun AppNavGraph(navController: NavHostController, authViewModel: AuthViewModel, startDestination: String = "login") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController = navController, viewModel = authViewModel) }
        composable("signup") { SignupScreen(navController = navController, viewModel = authViewModel) }
        composable("otp_verify") { OtpVerifyScreen(navController = navController, viewModel = authViewModel) }
        composable("profile_upload") { ProfileUploadScreen(navController = navController, viewModel = authViewModel) }
        composable("forgot_password") { ForgotPasswordScreen(navController = navController, viewModel = authViewModel) }
        composable("new_password") { NewPasswordScreen(navController = navController, viewModel = authViewModel) }
        composable("home") { HomeScreen(navController = navController, viewModel = authViewModel) }
    }
}