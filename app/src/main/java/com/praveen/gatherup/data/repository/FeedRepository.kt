package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.FeedService
import com.praveen.gatherup.security.TokenStore

class FeedRepository(
    private val api: FeedService,
    private val tokenStore: TokenStore
) {

    suspend fun fetchFeed() =
        api.getPersonalizedFeed(
            "Bearer ${tokenStore.getAccessToken()}"
        )
}
