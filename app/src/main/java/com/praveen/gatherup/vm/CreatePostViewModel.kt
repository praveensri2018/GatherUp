package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CreatePostState {
    object Idle : CreatePostState()
    object Loading : CreatePostState()
    object Success : CreatePostState()
    data class Error(val message: String) : CreatePostState()
}

class CreatePostViewModel(
    private val repo: PostRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CreatePostState>(CreatePostState.Idle)
    val state: StateFlow<CreatePostState> = _state

    fun createPost(title: String, body: String) {
        viewModelScope.launch {
            try {
                _state.value = CreatePostState.Loading
                val res = repo.createPost(title, body)

                if (res.isSuccessful) {
                    _state.value = CreatePostState.Success
                } else {
                    _state.value = CreatePostState.Error(
                        res.errorBody()?.string() ?: "Create post failed"
                    )
                }
            } catch (e: Exception) {
                _state.value = CreatePostState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }
}
