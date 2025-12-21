package com.praveen.gatherup.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostActionViewModel(
    private val repo: PostRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PostActionVM"
    }

    private val inProgress = mutableSetOf<String>()

    fun toggleLike(
        postId: String,
        liked: Boolean,
        onSuccess: () -> Unit
    ) {
        if (inProgress.contains(postId)) {
            Log.d(TAG, "Like ignored – already in progress for postId=$postId")
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "toggleLike START postId=$postId liked=$liked")
            inProgress.add(postId)

            try {
                if (liked) {
                    Log.d(TAG, "Calling UNLIKE API for postId=$postId")
                    repo.unlike(postId)
                } else {
                    Log.d(TAG, "Calling LIKE API for postId=$postId")
                    repo.like(postId)
                }

                Log.d(TAG, "API SUCCESS for postId=$postId")
                onSuccess()

            } catch (e: Exception) {
                Log.e(TAG, "toggleLike ERROR postId=$postId", e)
            } finally {
                inProgress.remove(postId)
                Log.d(TAG, "toggleLike END postId=$postId")
            }
        }
    }
}
