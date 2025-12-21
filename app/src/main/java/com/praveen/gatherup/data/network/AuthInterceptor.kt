package com.praveen.gatherup.data.network

import okhttp3.Interceptor
import okhttp3.Response
import android.util.Log
import com.praveen.gatherup.security.TokenStore

class AuthInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStore.getAccessToken()

        Log.d("AUTH", "Sending token = $token")

        val request = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}
