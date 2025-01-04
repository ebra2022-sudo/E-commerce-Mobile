package com.example.e_commerce_mobile.data.repositories

import com.example.e_commerce_mobile.data.remote.ApiService
import com.example.e_commerce_mobile.data.remote.UserLoginForm
import com.example.e_commerce_mobile.data.remote.UserRegisterForm
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun registerUser(userRegisterForm: UserRegisterForm) = apiService.registerUser(userRegisterForm)
    suspend fun loginUser(userLoginForm: UserLoginForm) = apiService.loginUser(userLoginForm)
}