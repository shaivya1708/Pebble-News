package com.example.sampleapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.data.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _user = MutableLiveData<String?>()
    val user: LiveData<String?> = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email and password cannot be empty"
            return
        }
        
        val result = repository.login(email, password)
        result.onSuccess {
            _user.value = it
            _error.value = null
        }.onFailure {
            _user.value = null
            _error.value = it.message
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email and password cannot be empty"
            return
        }
        if (password.length < 6) {
            _error.value = "Password must be at least 6 characters"
            return
        }
        
        val result = repository.register(email, password)
        result.onSuccess {
            _user.value = it
            _error.value = null
        }.onFailure {
            _user.value = null
            _error.value = it.message
        }
    }

    fun logout() {
        repository.logout()
        _user.value = null
    }
}