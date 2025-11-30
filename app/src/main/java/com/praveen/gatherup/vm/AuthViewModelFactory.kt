package com.praveen.gatherup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.praveen.gatherup.data.repository.AuthRepositoryImpl
import com.praveen.gatherup.security.TokenStore

class AuthViewModelFactory(
    private val repo: AuthRepositoryImpl,
    private val tokenStore: TokenStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo, tokenStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
