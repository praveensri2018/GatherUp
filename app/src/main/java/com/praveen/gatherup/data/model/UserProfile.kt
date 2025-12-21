package com.praveen.gatherup.data.model

data class UserProfile(
    val id: String,
    val username: String?,
    val displayName: String?,
    val email: String?,
    val mobileNumber: String?,
    val bio: String?,
    val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
    val posts: Int?,
    val createdAt: String
)
