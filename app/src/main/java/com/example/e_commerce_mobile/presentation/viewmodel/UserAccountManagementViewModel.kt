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
    private val authRepository: AuthRepository
): ViewModel() {

    private val _loginStatus = MutableStateFlow(false)
    val loginStatus: StateFlow<Boolean> get() = _loginStatus



    var signUpFirstName by mutableStateOf("")
    var signUpLastName by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var signUpPhoneNumber by mutableStateOf("")
    var passwordResetEmail by mutableStateOf("")
    var signUpPassword by mutableStateOf("")

    var logInEmail by mutableStateOf("")
    var logInPassword by mutableStateOf("")


    val onSignUpFirstNameChange = { text: String -> signUpFirstName = text }
    val onSignUpLastNameChange = { text: String -> signUpLastName = text }
    val onSignUpEmailChange = { text: String -> signUpEmail = text }
    val onSignUpPhoneNumberChange = { text: String -> signUpPhoneNumber = text }
    val onSignUpPasswordChange = { text: String -> signUpPassword = text }

    val onLogInEmailChange = { text: String -> logInEmail = text }
    val onLogInPasswordChange = { text: String -> logInPassword = text }


    val onPasswordResetEmailChange = { text: String -> passwordResetEmail = text }





    // Function to update the base URL


    // Example API interface


    fun registerUser() {
        val user = UserRegisterForm(
            firstName = signUpFirstName,
            lastName = signUpLastName,
            email = signUpEmail,
            phoneNumber = signUpPhoneNumber,
            password = signUpPassword
        )
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("View Model | register function", "User: $user")
            try {
                // design the system of
                // Make the network call directly using the ApiService
                val response = authRepository.registerUser(user)

                // Check if the response is successful
                Log.d("View Model | register function", "Response: $response ")

                    // Handle the error response

            } catch (e: Exception) {
                // Handle any exceptions, e.g., network errors
                Log.d("View Model | register function", "Error during registration: ${e.localizedMessage}")
            }
        }
    }


    fun loginUser() {
        val user = UserLoginForm(
            email = logInEmail,
            password = logInPassword
        )
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make the network call directly using the ApiService
                val response = authRepository.loginUser(user)
                if (response.isSuccessful) {
                    Log.d("View Model | LogIn function", "Response: $response")
                    _loginStatus.value = true

                }

                        // Show a success message or update UI accordingly
            } catch (e: Exception) {
                Log.d("View Model | LogIn function", "Error during login: ${e.localizedMessage}")
            }
        }
    }
}