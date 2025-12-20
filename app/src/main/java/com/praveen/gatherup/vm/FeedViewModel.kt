package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.api.FeedItemDto
import com.praveen.gatherup.data.repository.FeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/* ---------------- UI STATE ---------------- */

sealed class FeedUiState {

    object Loading : FeedUiState()

    data class Success(
        val items: List<FeedItemDto>,
        val loadingMore: Boolean,
        val endReached: Boolean
    ) : FeedUiState()

    data class Error(val message: String) : FeedUiState()
}

/* ---------------- VIEW MODEL ---------------- */

class FeedViewModel(
    private val repo: FeedRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val state: StateFlow<FeedUiState> = _state

    /* ---------------- INTERNAL STATE ---------------- */

    private val items = mutableListOf<FeedItemDto>()
    private var cursor: Double? = null
    private var loadingMore = false
    private var endReached = false
    private var initialized = false

    /* ---------------- INITIAL LOAD ---------------- */

    fun loadInitial() {
        if (initialized) return
        initialized = true
        loadMore()
    }

    /* ---------------- PAGINATION ---------------- */

    fun loadMore() {
        if (loadingMore || endReached) return

        viewModelScope.launch {
            try {
                loadingMore = true

                // show loader if first load
                if (items.isEmpty()) {
                    _state.value = FeedUiState.Loading
                } else {
                    _state.value = FeedUiState.Success(
                        items = items.toList(),
                        loadingMore = true,
                        endReached = false
                    )
                }

                val res = repo.loadFeed(
                    cursor = cursor,
                    limit = 10
                )

                if (res.items.isEmpty()) {
                    endReached = true
                } else {
                    items.addAll(res.items)
                    cursor = res.next_cursor
                }

                _state.value = FeedUiState.Success(
                    items = items.toList(),
                    loadingMore = false,
                    endReached = endReached
                )

            } catch (e: Exception) {

                _state.value = FeedUiState.Error(
                    e.message ?: "Failed to load feed"
                )

            } finally {
                loadingMore = false
            }
        }
    }
}
