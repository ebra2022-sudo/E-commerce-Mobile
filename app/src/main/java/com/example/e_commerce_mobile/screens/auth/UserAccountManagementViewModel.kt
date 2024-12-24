package com.example.e_commerce_mobile.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserAccountManagementViewModel: ViewModel() {
    var signUpName by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var logInEmail by mutableStateOf("")
    var passwordResetEmail by mutableStateOf("")
    var signUpPassword by mutableStateOf("")
    var logInPassword by mutableStateOf("")

    val onSignUpNameChange = { text: String -> signUpName = text }
    val onSignUpEmailChange = { text: String -> signUpEmail = text }
    val onSignUpPasswordChange = { text: String -> signUpPassword = text }
    val onLogInEmailChange = { text: String -> logInEmail = text }
    val onLogInPasswordChange = { text: String -> logInPassword = text }
    val onPasswordResetEmailChange = { text: String -> passwordResetEmail = text }

}