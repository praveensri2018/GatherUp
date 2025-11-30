package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.AuthRepositoryImpl
import com.praveen.gatherup.data.model.AuthTokens
import com.praveen.gatherup.security.TokenStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}

class AuthViewModel(
    private val repo: AuthRepositoryImpl,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<AuthTokens>>(UiState.Idle)
    val loginState: StateFlow<UiState<AuthTokens>> = _loginState

    private val _registerState = MutableStateFlow<UiState<AuthTokens>>(UiState.Idle)
    val registerState: StateFlow<UiState<AuthTokens>> = _registerState

    fun login(mobile: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val r = repo.login(mobile, password)
                if (r.isSuccessful) {
                    val tokens = r.body()
                    tokens?.let {
                        saveTokens(it)
                        _loginState.value = UiState.Success(it)
                    } ?: run {
                        _loginState.value = UiState.Error("Empty response")
                    }
                } else {
                    _loginState.value = UiState.Error(r.errorBody()?.string() ?: "Login failed")
                }
            } catch (t: Throwable) {
                _loginState.value = UiState.Error(t.message)
            }
        }
    }

    fun register(name: String?, mobile: String, password: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            try {
                val r = repo.register(name, mobile, password)
                if (r.isSuccessful) {
                    val tokens = r.body()
                    tokens?.let {
                        saveTokens(it)
                        _registerState.value = UiState.Success(it)
                    } ?: run {
                        _registerState.value = UiState.Error("Empty response")
                    }
                } else {
                    _registerState.value = UiState.Error(r.errorBody()?.string() ?: "Registration failed")
                }
            } catch (t: Throwable) {
                _registerState.value = UiState.Error(t.message)
            }
        }
    }

    private fun saveTokens(tokens: AuthTokens) {
        tokens.access_token?.let { tokenStore.saveAccessToken(it) }
        tokens.refresh_token?.let { tokenStore.saveRefreshToken(it) }
    }
}
