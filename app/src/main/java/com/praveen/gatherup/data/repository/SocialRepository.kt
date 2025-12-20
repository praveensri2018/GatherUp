package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.SocialService
import com.praveen.gatherup.security.TokenStore

class SocialRepository(
    private val api: SocialService,
    private val tokenStore: TokenStore
) {

    suspend fun bookmark(postId: String) {
        api.bookmark(
            token = "Bearer ${tokenStore.getAccessToken()}",
            postId = postId
        )
    }

    suspend fun unBookmark(postId: String) {
        api.unBookmark(
            token = "Bearer ${tokenStore.getAccessToken()}",
            postId = postId
        )
    }

    suspend fun follow(userId: String) {
        api.follow(
            token = "Bearer ${tokenStore.getAccessToken()}",
            userId = userId
        )
    }

    suspend fun unFollow(userId: String) {
        api.unFollow(
            token = "Bearer ${tokenStore.getAccessToken()}",
            userId = userId
        )
    }
}
