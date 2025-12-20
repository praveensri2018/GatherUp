package com.praveen.gatherup.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {

    @GET("/posts/{id}/comments")
    suspend fun list(
        @Header("Authorization") token: String,
        @Path("id") postId: String
    ): Response<List<CommentDto>>

    @POST("/posts/{id}/comments")
    suspend fun add(
        @Header("Authorization") token: String,
        @Path("id") postId: String,
        @Body body: Map<String, String>
    ): Response<Unit>
}
