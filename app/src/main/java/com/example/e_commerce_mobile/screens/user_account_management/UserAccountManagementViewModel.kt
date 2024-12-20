package com.example.e_commerce_mobile.screens.user_account_management

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserAccountManagementViewModel: ViewModel() {
    var signUpName by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var signUpPassword by mutableStateOf("")

    val onSignUpNameChange = { text: String -> signUpName = text }
    val onSignUpEmailChange = { text: String -> signUpEmail = text }
    val onSignUpPasswordChange = { text: String -> signUpPassword = text }

}