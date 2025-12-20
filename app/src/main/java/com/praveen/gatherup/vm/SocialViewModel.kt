package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.SocialRepository
import kotlinx.coroutines.launch

class SocialViewModel(
    private val repo: SocialRepository
) : ViewModel() {

    /* ---------------- BOOKMARK ---------------- */

    fun bookmark(postId: String) {
        viewModelScope.launch {
            try {
                repo.bookmark(postId)
            } catch (_: Exception) {}
        }
    }

    fun unbookmark(postId: String) {
        viewModelScope.launch {
            try {
                repo.unbookmark(postId)
            } catch (_: Exception) {}
        }
    }

    fun toggleBookmark(postId: String, bookmarked: Boolean) {
        viewModelScope.launch {
            try {
                if (bookmarked) {
                    repo.unbookmark(postId)
                } else {
                    repo.bookmark(postId)
                }
            } catch (_: Exception) {}
        }
    }

    /* ---------------- FOLLOW ---------------- */

    fun follow(userId: String) {
        viewModelScope.launch {
            try {
                repo.follow(userId)
            } catch (_: Exception) {}
        }
    }

    fun unfollow(userId: String) {
        viewModelScope.launch {
            try {
                repo.unfollow(userId)
            } catch (_: Exception) {}
        }
    }

    fun toggleFollow(userId: String, following: Boolean) {
        viewModelScope.launch {
            try {
                if (following) {
                    repo.unfollow(userId)
                } else {
                    repo.follow(userId)
                }
            } catch (_: Exception) {}
        }
    }
}
