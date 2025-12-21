package com.praveen.gatherup.data.repository

import com.praveen.gatherup.data.api.UserService
import com.praveen.gatherup.data.model.MeDto
import com.praveen.gatherup.data.model.ProfileDto
import com.praveen.gatherup.security.TokenStore
import retrofit2.Response

class UserRepository(
    private val userService: UserService,
    private val tokenStore: TokenStore
) {

    /* ---------- /api/me ---------- */
    suspend fun getMe(): Result<MeDto> {
        val token = tokenStore.getAccessToken()
            ?: return Result.failure(Exception("Access token missing"))

        return try {
            val response: Response<MeDto> =
                userService.getMe("Bearer $token")

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    // cache profile
                    tokenStore.saveMyProfile(body)
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(
                    Exception("HTTP ${response.code()} : ${response.message()}")
                )
            }

        } catch (e: Exception) {
            // network error → fallback to cache
            val cached = tokenStore.getCachedProfile()

            if (cached != null) {
                Result.success(cached)
            } else {
                Result.failure(e)
            }
        }
    }

    /* ---------- /users/{id} ---------- */
    suspend fun getUserProfile(userId: String): Result<ProfileDto> {
        val token = tokenStore.getAccessToken()
            ?: return Result.failure(Exception("Access token missing"))

        return try {
            val response: Response<ProfileDto> =
                userService.getUserProfile(
                    token = "Bearer $token",
                    userId = userId
                )

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(
                    Exception("HTTP ${response.code()} : ${response.message()}")
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
