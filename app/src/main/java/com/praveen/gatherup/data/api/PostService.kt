package com.praveen.gatherup.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @POST("/posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body body: CreatePostRequest
    ): Response<Unit>

    @POST("/posts/{postId}/reactions")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: String
    ): Response<Unit>

    @DELETE("/posts/{postId}/reactions")
    suspend fun unlikePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: String
    ): Response<Unit>
}
