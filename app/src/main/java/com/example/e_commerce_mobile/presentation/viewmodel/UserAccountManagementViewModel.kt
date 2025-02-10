package com.example.e_commerce_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_mobile.data.remote.UserLoginForm
import com.example.e_commerce_mobile.data.remote.UserLoginResponseWithMessage
import com.example.e_commerce_mobile.data.remote.UserRegisterForm
import com.example.e_commerce_mobile.data.repositories.AuthRepository
import com.example.e_commerce_mobile.utils.CurrentUser
import com.example.e_commerce_mobile.utils.UserPreferences
import com.example.e_commerce_mobile.utils.UserSession
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject


@HiltViewModel
class UserAccountManagementViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userSession: UserSession
): ViewModel() {

    val accessToken: StateFlow<String?> = userSession.accessToken
    val isLoggedIn: StateFlow<Boolean> = userSession.isLoggedIn

    init {

        Log.d("ViewModel | accessToken", accessToken.value.toString())
        Log.d("ViewModel | isLoggedIn", isLoggedIn.value.toString())
    }


    // Sign up fields
    var signUpFirstName by mutableStateOf("")
    var signUpLastName by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var signUpPhoneNumber by mutableStateOf("")
    var signUpPassword by mutableStateOf("")

    // Login fields
    var logInEmail by mutableStateOf("")
    var logInPassword by mutableStateOf("")

    // Password reset fields
    var passwordResetEmail by mutableStateOf("")

    // UI binding functions
    val onSignUpFirstNameChange = { text: String -> signUpFirstName = text }
    val onSignUpLastNameChange = { text: String -> signUpLastName = text }
    val onSignUpEmailChange = { text: String -> signUpEmail = text }
    val onSignUpPhoneNumberChange = { text: String -> signUpPhoneNumber = text }
    val onSignUpPasswordChange = { text: String -> signUpPassword = text }

    val onLogInEmailChange = { text: String -> logInEmail = text }
    val onLogInPasswordChange = { text: String -> logInPassword = text }

    val onPasswordResetEmailChange = { text: String -> passwordResetEmail = text }

    // Function to register a user
    fun registerUser() {
        val user = UserRegisterForm(
            firstName = signUpFirstName,
            lastName = signUpLastName,
            email = signUpEmail,
            phoneNumber = signUpPhoneNumber,
            password = signUpPassword
        )
        // Launch the network call in a coroutine
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.registerUser(user)

                if (response.isSuccessful) {
                    // Handle successful registration logic
                    Log.d("ViewModel | registerUser", "Registration successful: $response")
                } else {
                    // Handle unsuccessful registration (e.g., error messages)
                    Log.d("ViewModel | registerUser", "Registration failed: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.d("ViewModel | registerUser", "Error during registration: ${e.localizedMessage}")
            }
        }
    }

    // Function to log in a user
    fun loginUser() {
        val user = UserLoginForm(email = logInEmail, password = logInPassword)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.loginUser(user)

                if (response.isSuccessful) {
                    response.body()?.accessToken?.let { token ->
                        // Save token and update login state
                        userSession.updateUserSession(response.body()?.accessToken.toString(),

                            response.body()?.userId.toString()
                        )

                    }

                    response.body()?.let { userData ->
                        // Store the current user data for the session
                        CurrentUser.token = userData.accessToken
                        CurrentUser.userId = userData.userId.toString()
                    }
                } else {
                    Log.d("ViewModel | loginUser", "Login failed: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.d("ViewModel | loginUser", "Error during login: ${e.localizedMessage}")
            }
        }
    }

    // Function to log out a user


    fun logoutUser() {
        // Make the API call to logout
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.logoutUser(accessToken.value.toString())
                if (response.isSuccessful) {
                    // Call the method to clear the token from the local storage

                    userSession.clearUserSession()
                    CurrentUser.token = ""
                    CurrentUser.userId = ""
                } else {
                    // Handle error if needed
                    Log.d("Logout", "Logout failed: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("Logout", "Error during logout: ${e.localizedMessage}")
            }
        }
    }
}
