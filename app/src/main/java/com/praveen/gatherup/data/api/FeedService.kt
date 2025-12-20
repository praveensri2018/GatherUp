package com.praveen.gatherup.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FeedService {

    @GET("/posts/feed/personalized")
    suspend fun getPersonalizedFeed(
        @Header("Authorization") token: String
    ): Response<List<FeedPostDto>>
}