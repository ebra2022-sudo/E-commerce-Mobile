package com.example.e_commerce_mobile.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor(
    private val userPreferences: UserPreferences
) {
    private val _accessToken = MutableStateFlow<String?>(null)
    private val _isLoggedIn = MutableStateFlow(false)
    private val _userId = MutableStateFlow<String?>(null)

    val accessToken: StateFlow<String?> get() = _accessToken
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn
    val userId: StateFlow<String?> get() = _userId

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // Launch a coroutine to observe changes in user session data (tokens and login state)
        coroutineScope.launch {
            userPreferences.accessTokenFlow.collect { token ->
                _accessToken.value = token
            }
        }
        coroutineScope.launch {
            userPreferences.isLoggedInFlow.collect { loggedIn ->
                _isLoggedIn.value = loggedIn
            }
        }
        coroutineScope.launch {
            userPreferences.userIdFlow.collect { userId ->
                _userId.value = userId
            }
        }
    }

    suspend fun updateUserSession(accessToken: String, userId: String) {
        _accessToken.value = accessToken
        _isLoggedIn.value = true

        // Save both tokens and update the login state
        userPreferences.saveUserSession(accessToken, userId)
    }

    suspend fun clearUserSession() {
        _accessToken.value = null
        _isLoggedIn.value = false

        // Clear session data in preferences
        userPreferences.clearUserSession()
    }

    // Cleanup if needed
    fun clearSession() {
        coroutineScope.cancel() // Cancels all coroutines started in this scope
    }
}

