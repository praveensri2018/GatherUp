    package com.praveen.gatherup.vm

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.praveen.gatherup.data.api.FeedItemDto
    import com.praveen.gatherup.data.api.StatsDto
    import com.praveen.gatherup.data.api.ViewerStateDto
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

        private var cursor: Double? = null
        private var loadingMore = false
        private var endReached = false
        private var initialized = false

        fun loadInitial() {
            if (initialized) return
            initialized = true
            loadMore()
        }

        fun loadMore() {
            if (loadingMore || endReached) return

            viewModelScope.launch {
                loadingMore = true

                val currentItems =
                    (_state.value as? FeedUiState.Success)?.items ?: emptyList()

                _state.value = FeedUiState.Success(
                    items = currentItems,
                    loadingMore = true,
                    endReached = false
                )

                try {
                    val res = repo.loadFeed(cursor, 10)

                    cursor = res.next_cursor
                    endReached = res.items.isEmpty()

                    _state.value = FeedUiState.Success(
                        items = currentItems + res.items,
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

        /* ---------- LIKE STATE ---------- */

        fun updateLikeState(postId: String) {
            val current = _state.value
            if (current is FeedUiState.Success) {
                _state.value = current.copy(
                    items = current.items.map { item ->
                        if (item.post_id == postId) {

                            val wasLiked = item.viewer_state?.liked ?: false
                            val currentLikes = item.stats?.likes ?: 0

                            item.copy(
                                viewer_state = item.viewer_state?.copy(
                                    liked = !wasLiked
                                ) ?: ViewerStateDto(liked = true, bookmarked = false),

                                stats = item.stats?.copy(
                                    likes = if (wasLiked)
                                        maxOf(0, currentLikes - 1)
                                    else
                                        currentLikes + 1
                                ) ?: StatsDto(
                                    likes = 1,
                                    comments = 0,
                                    bookmarks = 0
                                ),

                                media = item.media ?: emptyList()   // 🔥 IMPORTANT
                            )
                        } else item
                    }
                )
            }
        }

    }

