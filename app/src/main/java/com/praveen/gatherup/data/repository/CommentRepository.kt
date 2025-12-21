package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.CommentService
import com.praveen.gatherup.security.TokenStore

class CommentRepository(
    private val api: CommentService,
    private val tokenStore: TokenStore
) {

    suspend fun list(postId: String) =
        api.list(
            token = "Bearer ${tokenStore.getAccessToken()}",
            postId = postId
        )

    suspend fun add(postId: String, body: String) =
        api.add(
            token = "Bearer ${tokenStore.getAccessToken()}",
            postId = postId,
            body = mapOf("body" to body)
        )
}
