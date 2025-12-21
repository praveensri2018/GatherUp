package com.praveen.gatherup.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.praveen.gatherup.ui.screens.onboarding.SplashScreen
import com.praveen.gatherup.ui.screens.auth.*
import com.praveen.gatherup.ui.screens.home.HomeScreen

import com.praveen.gatherup.ui.screens.feed.*
import com.praveen.gatherup.ui.screens.misc.PrivacyScreen
import com.praveen.gatherup.ui.screens.misc.TermsScreen
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.json.JSONObject
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.praveen.gatherup.ui.screens.profile.EditProfileScreen
import com.praveen.gatherup.ui.screens.profile.ProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = "splash"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("splash") { SplashScreen(navController) }

        // Auth / Legal
        composable(
            route = "login_form?mobile={mobile}",
            arguments = listOf(
                navArgument("mobile") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val prefillMobile = backStackEntry.arguments?.getString("mobile") ?: ""
            LoginFormScreen(navController = navController, prefillMobile = prefillMobile)
        }
        composable("register") {
            val scope = rememberCoroutineScope()
            var apiError by remember { mutableStateOf<String?>(null) }

            RegisterScreen(
                apiError = apiError,
                onRegister = { name, mobile, password ->
                    scope.launch {
                        apiError = null

                        try {
                            // TODO: replace with your real backend call
                            val response = registerUserApi(name, mobile, password)

                            if (response.isSuccessful) {
                                // success -> go to success screen
                                navController.navigate("auth_success") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                // error body example: { "error": "user already exists" }
                                val errorBody = response.errorBody
                                apiError = extractErrorMessage(errorBody)
                            }
                        } catch (e: CancellationException) {
                            // ignore coroutine cancellation
                            throw e
                        } catch (e: Exception) {
                            apiError = "Something went wrong. Please try again."
                        }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("auth_success") { AuthSuccessScreen(navController) }


        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("otp_verify/{mobile}") { backStackEntry ->
            val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
            OtpVerifyScreen(navController, mobile)
        }

        composable("reset_password/{mobile}") { backStackEntry ->
            val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
            ResetPasswordScreen(navController, mobile)
        }

        // Legal pages
        composable("terms") { TermsScreen(navController) }
        composable("privacy") { PrivacyScreen(navController) }

        // Home
      //  composable("home") { FeedScreen(navController) }
        composable("home") {
            HomeScreen(navController)
        }

        composable("feed") {
            FeedScreen(navController)
        }

        composable("profile/me") {
            ProfileScreen(
                navController = navController,
                isMe = true
            )
        }

        composable("edit_profile") { EditProfileScreen() }

        composable("create_post") {
            PostComposerScreen(navController)
        }
        composable(
            route = "post_detail/{postId}",
            arguments = listOf(
                navArgument("postId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")!!
            PostDetailScreen(
                navController = navController,
                postId = postId
            )
        }
    }
}

/**
 * Dummy structure to represent your API response.
 * Replace with Retrofit / Ktor response as needed.
 */
data class RegisterResponse(
    val isSuccessful: Boolean,
    val body: String? = null,
    val errorBody: String? = null
)

// Dummy implementation – replace with real one.
suspend fun registerUserApi(
    name: String,
    mobile: String,
    password: String
): RegisterResponse {
    // Example:
    // - If user already exists -> error JSON from backend
    // Here just mimic "user already exists"
    return RegisterResponse(
        isSuccessful = false,
        errorBody = """{"error": "user already exists"}"""
    )
}

fun extractErrorMessage(errorBody: String?): String {
    if (errorBody.isNullOrBlank()) return "Registration failed. Please try again."
    return try {
        val obj = JSONObject(errorBody)
        val err = obj.optString("error")
        if (err.isNullOrBlank()) "Registration failed. Please try again." else err
    } catch (e: Exception) {
        "Registration failed. Please try again."
    }
}
