// PLACE IN: app/src/main/java/com/praveen/gatherup/viewmodel/AuthViewModel.kt
package com.praveen.gatherup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.praveen.gatherup.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {
    // TODO: Implement login, signup, OTP verify, and reset password flows using repo
    fun login(mobile: String, password: String) = viewModelScope.launch {
        // Example: repo.login(mobile, password)
    }

    fun signup(name: String, mobile: String, dob: String, password: String) = viewModelScope.launch {
        // Example: repo.signup(name, mobile, dob, password)
    }

    fun verifyOtp(mobile: String, otp: String) = viewModelScope.launch {
        // Example: repo.verifyOtp(mobile, otp)
    }

    fun resetPassword(mobile: String, newPassword: String) = viewModelScope.launch {
        // Example: repo.resetPassword(mobile, newPassword)
    }
}

class AuthViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
