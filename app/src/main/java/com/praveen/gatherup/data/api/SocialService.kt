package com.praveen.gatherup.data.api

import retrofit2.Response
import retrofit2.http.*

interface SocialService {

    @POST("/posts/{id}/bookmark")
    suspend fun bookmark(
        @Header("Authorization") token: String,
        @Path("id") postId: String
    ): Response<Unit>

    @DELETE("/posts/{id}/bookmark")
    suspend fun unbookmark(
        @Header("Authorization") token: String,
        @Path("id") postId: String
    ): Response<Unit>

    @POST("/users/{id}/follow")
    suspend fun follow(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Response<Unit>

    @POST("/users/{id}/unfollow")
    suspend fun unfollow(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Response<Unit>
}
