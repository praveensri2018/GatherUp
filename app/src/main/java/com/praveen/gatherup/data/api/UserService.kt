package com.praveen.gatherup.data.api

import com.praveen.gatherup.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService {

    @GET("/api/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): Response<MeDto>

    @GET("/users/{id}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Response<ProfileDto>
}