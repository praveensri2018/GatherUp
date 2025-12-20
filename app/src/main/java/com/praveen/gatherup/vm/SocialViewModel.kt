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
            } catch (e: Exception) {
                // optional: log error
            }
        }
    }

    fun unBookmark(postId: String) {
        viewModelScope.launch {
            try {
                repo.unBookmark(postId)
            } catch (e: Exception) {
                // optional: log error
            }
        }
    }

    /* ---------------- FOLLOW ---------------- */

    fun follow(userId: String) {
        viewModelScope.launch {
            try {
                repo.follow(userId)
            } catch (e: Exception) {
                // optional: log error
            }
        }
    }

    fun unFollow(userId: String) {
        viewModelScope.launch {
            try {
                repo.unFollow(userId)
            } catch (e: Exception) {
                // optional: log error
            }
        }
    }
}
