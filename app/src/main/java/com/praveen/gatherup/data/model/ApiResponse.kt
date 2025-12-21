package com.praveen.gatherup.data.model

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String? = null,
    val error_code: String? = null
)