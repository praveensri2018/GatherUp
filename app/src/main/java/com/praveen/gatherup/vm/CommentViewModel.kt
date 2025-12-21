package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.api.CommentDto
import com.praveen.gatherup.data.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CommentUiState(
    val comments: List<CommentDto> = emptyList()
)

class CommentViewModel(
    private val repo: CommentRepository,
    private val postId: String
) : ViewModel() {

    private val _state = MutableStateFlow(CommentUiState())
    val state: StateFlow<CommentUiState> = _state

    init {
        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {
            val res = repo.list(postId)
            if (res.isSuccessful) {
                _state.value = CommentUiState(res.body() ?: emptyList())
            }
        }
    }

    fun addComment(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            repo.add(postId, text)
            loadComments() // refresh
        }
    }
}
