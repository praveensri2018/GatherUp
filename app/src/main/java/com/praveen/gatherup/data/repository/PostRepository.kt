package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.CreatePostRequest
import com.praveen.gatherup.data.api.PostService
import com.praveen.gatherup.security.TokenStore

class PostRepository(
    private val api: PostService,
    private val tokenStore: TokenStore
) {

    suspend fun createPost(
        title: String,
        body: String
    ) = api.createPost(
        token = "Bearer ${tokenStore.getAccessToken()}",
        body = CreatePostRequest(
            title = title,
            body = body
        )
    )

    suspend fun like(postId: String) =
        api.likePost("Bearer ${tokenStore.getAccessToken()}", postId)

    suspend fun unlike(postId: String) =
        api.unlikePost("Bearer ${tokenStore.getAccessToken()}", postId)

}
