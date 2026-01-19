package com.example.sampleapp.data

/**
 * Authentication repository using local storage
 */
class AuthRepository(private val localStorage: LocalStorageManager) {

    fun login(email: String, password: String): Result<String> {
        return if (localStorage.loginUser(email, password)) {
            Result.success(email)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }

    fun register(email: String, password: String): Result<String> {
        return if (localStorage.registerUser(email, password)) {
            Result.success(email)
        } else {
            Result.failure(Exception("User already exists"))
        }
    }

    fun logout() {
        localStorage.logoutUser()
    }

    fun getCurrentUser(): String? = localStorage.getCurrentUserEmail()

    fun isLoggedIn(): Boolean = localStorage.isLoggedIn()
}