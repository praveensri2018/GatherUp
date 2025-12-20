package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.SocialService
import com.praveen.gatherup.security.TokenStore

class SocialRepository(
    private val api: SocialService,
    private val tokenStore: TokenStore
) {

    suspend fun bookmark(postId: String) {
        api.bookmark(
            "Bearer ${tokenStore.getAccessToken()}",
            postId
        )
    }

    suspend fun unbookmark(postId: String) {
        api.unbookmark(
            "Bearer ${tokenStore.getAccessToken()}",
            postId
        )
    }

    suspend fun follow(userId: String) {
        api.follow(
            "Bearer ${tokenStore.getAccessToken()}",
            userId
        )
    }

    suspend fun unfollow(userId: String) {
        api.unfollow(
            "Bearer ${tokenStore.getAccessToken()}",
            userId
        )
    }
}
