package com.praveen.gatherup.data.api

import retrofit2.Response
import retrofit2.http.*

interface FeedService {

    @GET("/posts/feed/personalized")
    suspend fun getFeed(
        @Header("Authorization") token: String,
        @Query("cursor") cursor: Double?,
        @Query("limit") limit: Int
    ): Response<FeedResponse>
}

data class FeedResponse(
    val items: List<FeedItemDto>,
    val next_cursor: Double?
)

data class FeedItemDto(
    val post_id: String,
    val author: AuthorDto,
    val content: ContentDto,
    val stats: StatsDto,
    val viewer_state: ViewerStateDto,
    val created_at: String,
    val score: Double,
    val media: List<MediaDto>? = emptyList()
)

data class AuthorDto(
    val id: String,
    val username: String,
    val is_following: Boolean
)

data class ContentDto(
    val title: String?,
    val body: String?,
    val kind: String
)

data class StatsDto(
    val likes: Int,
    val comments: Int,
    val bookmarks: Int
)

data class ViewerStateDto(
    val liked: Boolean,
    val bookmarked: Boolean
)

data class MediaDto(
    val media_url: String,
    val media_type: String,
    val file_size: Long,
    val sort_order: Int
)