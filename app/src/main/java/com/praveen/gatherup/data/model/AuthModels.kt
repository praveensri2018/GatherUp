package com.praveen.gatherup.data.model

data class LoginRequest(val mobile_number: String, val password: String)
data class AuthTokens(val access_token: String?, val refresh_token: String?)
data class RegisterRequest(val mobile_number: String, val password: String, val full_name: String?)

