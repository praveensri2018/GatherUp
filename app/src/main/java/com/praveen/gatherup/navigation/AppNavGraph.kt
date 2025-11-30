package com.praveen.gatherup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.praveen.gatherup.ui.screens.onboarding.SplashScreen
import com.praveen.gatherup.ui.screens.auth.*
import com.praveen.gatherup.ui.screens.home.HomeScreen
import com.praveen.gatherup.ui.screens.misc.PrivacyScreen
import com.praveen.gatherup.ui.screens.misc.TermsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = "splash"
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("splash") { SplashScreen(navController) }

        // Auth / Legal
        composable("login") { LoginScreen(navController) }
        composable("login_form") { LoginFormScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("otp_verify") { OtpVerifyScreen(navController) }
        composable("reset_password") { ResetPasswordScreen(navController) }
        composable("auth_success") { AuthSuccessScreen(navController) }

        // Legal pages
        composable("terms") { TermsScreen(navController) }
        composable("privacy") { PrivacyScreen(navController) }

        // Home
        composable("home") { HomeScreen() }
    }
}
