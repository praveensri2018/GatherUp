package com.praveen.gatherup.data

import com.praveen.gatherup.data.api.AuthService
import com.praveen.gatherup.data.api.FeedService
import com.praveen.gatherup.data.api.PostService
import com.praveen.gatherup.data.api.SocialService
import com.praveen.gatherup.data.api.CommentService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val BASE_URL = "https://gatherupapi.run.place"
    //private const val BASE_URL = "http://localhost:8080"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val feedService: FeedService by lazy {
        retrofit.create(FeedService::class.java)
    }

    val postService: PostService by lazy {
        retrofit.create(PostService::class.java)
    }

    val socialService: SocialService by lazy {
        retrofit.create(SocialService::class.java)
    }
    val commentService: CommentService by lazy {
        retrofit.create(CommentService::class.java)
    }

}
