package com.praveen.gatherup.data.api

data class CommentDto(
    val id: String,
    val body: String,
    val created_at: String,
    val author_id: String
)

data class CreatePostRequest(
    val title: String,
    val body: String,
    val kind: String = "text"
)
