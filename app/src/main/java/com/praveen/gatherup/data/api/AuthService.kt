package com.praveen.gatherup.data.api

import com.praveen.gatherup.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body body: LoginRequest): Response<AuthTokens>

    @POST("/auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthTokens>

    // optional: get profile
    @GET("/api/me")
    suspend fun me(@Header("Authorization") bearer: String): Response<Any>

    @POST("/auth/forgot/send-otp")
    suspend fun sendForgotOtp(@Body body: SendOtpRequest): Response<SendOtpResponse>

    @POST("/auth/forgot/verify-otp")
    suspend fun verifyForgotOtp(@Body body: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("/auth/forgot/reset")
    suspend fun resetPassword(@Body body: ResetPasswordRequest): Response<SimpleApiResponse>
}
