package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.FeedResponse
import com.praveen.gatherup.data.api.FeedService
import com.praveen.gatherup.security.TokenStore

class FeedRepository(
    private val api: FeedService,
    private val tokenStore: TokenStore
) {

    suspend fun loadFeed(
        cursor: Double?,
        limit: Int
    ): FeedResponse {

        val token = tokenStore.getAccessToken()
            ?: throw IllegalStateException("Token missing")

        return api.getFeed(
            token = "Bearer $token",
            cursor = cursor,
            limit = limit
        ).body() ?: FeedResponse(emptyList(), null)
    }
}