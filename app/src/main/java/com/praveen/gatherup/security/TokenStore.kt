package com.praveen.gatherup.security

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.praveen.gatherup.data.model.MeDto

class TokenStore(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_tokens", Context.MODE_PRIVATE)

    private val gson = Gson()

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


    fun saveMyProfile(me: MeDto) {
        val json = gson.toJson(me)
        prefs.edit().putString("cached_profile", json).apply()
    }

    fun getCachedProfile(): MeDto? {
        val json = prefs.getString("cached_profile", null) ?: return null
        return try {
            gson.fromJson(json, MeDto::class.java)
        } catch (e: Exception) {
            null
        }
    }

}
