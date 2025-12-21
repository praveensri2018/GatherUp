package com.praveen.gatherup.data.model

data class ProfileDto(
    val id: String,
    val display_name: String?,
    val username: String?,
    val avatar_url: String?,
    val bio: String?,
    val stats: ProfileStats
)


