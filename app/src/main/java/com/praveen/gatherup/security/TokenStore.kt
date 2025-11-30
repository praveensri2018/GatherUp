package com.praveen.gatherup.security

import android.content.Context
import android.content.SharedPreferences

class TokenStore(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_tokens", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        prefs.edit().putString("access_token", token).apply()
    }

    fun saveRefreshToken(token: String) {
        prefs.edit().putString("refresh_token", token).apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString("refresh_token", null)
    }

    fun clearTokens() {
        prefs.edit().clear().apply()
    }
}
