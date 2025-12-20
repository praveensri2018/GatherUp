package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.api.FeedPostDto
import com.praveen.gatherup.data.repository.FeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FeedUiState {
    object Loading : FeedUiState()
    data class Success(val posts: List<FeedPostDto>) : FeedUiState()
    data class Error(val message: String) : FeedUiState()
}

class FeedViewModel(
    private val repo: FeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val state: StateFlow<FeedUiState> = _state

    fun loadFeed() {
        viewModelScope.launch {
            try {
                val res = repo.fetchFeed()

                if (res.isSuccessful) {
                    _state.value =
                        FeedUiState.Success(res.body() ?: emptyList())
                } else {
                    _state.value =
                        FeedUiState.Error(res.errorBody()?.string() ?: "API Error")
                }

            } catch (e: Exception) {
                _state.value =
                    FeedUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
