package com.praveen.gatherup.data.api

data class FeedPostDto(
    val id: String,
    val title: String?,
    val body: String?,
    val created_at: String,
    val author_id: String,
    val kind: String,
    val media: Any? // null now
)
