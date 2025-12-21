package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.model.MeDto
import com.praveen.gatherup.data.repository.UserRepository
import com.praveen.gatherup.security.TokenStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val me: MeDto) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}


class ProfileViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    fun loadMyProfile() {
        viewModelScope.launch {
            _state.value = ProfileUiState.Loading

            repo.getMe()
                .onSuccess { me ->
                    _state.value = ProfileUiState.Success(me)
                }
                .onFailure { error ->
                    _state.value = ProfileUiState.Error(
                        error.message ?: "Failed to load profile"
                    )
                }
        }
    }
}