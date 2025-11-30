package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.AuthService
import com.praveen.gatherup.data.model.AuthTokens
import com.praveen.gatherup.data.model.LoginRequest
import com.praveen.gatherup.data.model.RegisterRequest
import retrofit2.Response

class AuthRepositoryImpl(private val api: AuthService) {

    suspend fun login(mobile: String, password: String): Response<AuthTokens> {
        return api.login(LoginRequest(mobile_number = mobile, password = password))
    }

    suspend fun register(name: String?, mobile: String, password: String): Response<AuthTokens> {
        return api.register(RegisterRequest(full_name = name, mobile_number = mobile, password = password))
    }
}
