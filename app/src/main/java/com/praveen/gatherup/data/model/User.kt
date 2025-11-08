package com.praveen.gatherup.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val mobile: String = "",
    val email: String? = null,
    val dob: String? = null,         // yyyy-MM-dd (optional)
    val profileUrl: String? = null,  // remote storage URL for profile picture
    val createdAt: String? = null    // ISO timestamp, set by backend
)