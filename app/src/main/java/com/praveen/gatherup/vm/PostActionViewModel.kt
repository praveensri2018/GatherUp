package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostActionViewModel(
    private val repo: PostRepository
) : ViewModel() {

    fun toggleLike(postId: String, liked: Boolean) {
        viewModelScope.launch {
            try {
                if (liked) repo.unlike(postId)
                else repo.like(postId)
            } catch (_: Exception) {}
        }
    }
}