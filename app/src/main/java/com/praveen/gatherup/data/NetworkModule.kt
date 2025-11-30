package com.praveen.gatherup.data

import com.praveen.gatherup.data.api.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    // Use 10.0.2.2 to reach host machine from emulator. Update to your base URL.
    private const val BASE_URL = "https://gatherupapi.run.place"
    //private const val BASE_URL = "http://localhost:8080"

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}
