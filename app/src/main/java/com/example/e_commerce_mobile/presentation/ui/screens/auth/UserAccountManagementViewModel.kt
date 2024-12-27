package com.example.e_commerce_mobile.presentation.ui.screens.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request


interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body userRegisterForm: UserRegisterForm): Response<UserRegisterResponseWithMessage>

    @POST("user/login")
    suspend fun loginUser(@Body userLoginForm: UserLoginForm): Response<UserLoginResponseWithMessage>
}



data class UserLoginForm(
    val email: String,
    val password: String
)

data class UserLoginResponseWithMessage(
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("access_token") val accessToken: String,
)






data class UserRegisterForm(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val password: String
)

data class UserRegisterResponse(
    val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
)

data class UserRegisterResponseWithMessage(
    @SerializedName("status_message") val statusMessage: String,
    val user: UserRegisterResponse
)




class BaseUrlInterceptor : Interceptor {
    @Volatile
    private var baseUrl: HttpUrl? = null

    fun setBaseUrl(newBaseUrl: String) {
        this.baseUrl = HttpUrl.get(newBaseUrl)
    }

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest: Request = chain.request()
        val currentBaseUrl = baseUrl ?: return chain.proceed(originalRequest)

        // Replace the base URL in the original request
        val newUrl = originalRequest.url().newBuilder()
            .scheme(currentBaseUrl.scheme())
            .host(currentBaseUrl.host())
            .port(currentBaseUrl.port())
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}



class UserAccountManagementViewModel: ViewModel() {

    private val _loginStatus = MutableStateFlow("")
    val loginStatus: StateFlow<String> get() = _loginStatus



    var signUpFirstName by mutableStateOf("")
    var signUpLastName by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var signUpPhoneNumber by mutableStateOf("")
    var passwordResetEmail by mutableStateOf("")
    var signUpPassword by mutableStateOf("")

    var logInEmail by mutableStateOf("")
    var logInPassword by mutableStateOf("")
    var logInIp by mutableStateOf("")

    val onSignUpFirstNameChange = { text: String -> signUpFirstName = text }
    val onSignUpLastNameChange = { text: String -> signUpLastName = text }
    val onSignUpEmailChange = { text: String -> signUpEmail = text }
    val onSignUpPhoneNumberChange = { text: String -> signUpPhoneNumber = text }
    val onSignUpPasswordChange = { text: String -> signUpPassword = text }

    val onLogInEmailChange = { text: String -> logInEmail = text }
    val onLogInPasswordChange = { text: String -> logInPassword = text }
    val onLogInIpChange = { text: String -> logInIp = text }

    val onPasswordResetEmailChange = { text: String -> passwordResetEmail = text }



    private val baseUrlInterceptor = BaseUrlInterceptor()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://default.base.url/") // Default base URL
        .client(
            OkHttpClient.Builder()
                .addInterceptor(baseUrlInterceptor)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Function to update the base URL
    fun updateBaseUrl() {
        baseUrlInterceptor.setBaseUrl("http://$logInIp:8000/")
    }

    // Example API interface
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun registerUser() {
        val user = UserRegisterForm(
            firstName = signUpFirstName,
            lastName = signUpLastName,
            email = signUpEmail,
            phoneNumber = signUpPhoneNumber,
            password = signUpPassword
        )
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            try {
                // Make the network call directly using the ApiService
                val response: Response<UserRegisterResponseWithMessage> = getApiService().registerUser(user)

                // Check if the response is successful
                if (response.isSuccessful) {
                    val result = response.body()
                    // Handle successful registration
                    if (result != null) {
                        // Show a success message or update UI accordingly
                        Log.d("SignUpViewModel","Registration successful: ${result.statusMessage}")
                    } else {
                        // Handle the case where the body is null
                        Log.d("SignUpViewModel","Unexpected response body")
                    }
                } else {
                    // Handle the error response
                    Log.d("SignUpViewModel", "Registration failed: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle any exceptions, e.g., network errors
                Log.d("SignUpViewModel", "Error during registration: ${e.localizedMessage}")
            }
        }
    }

    fun loginUser() {
        val user = UserLoginForm(
            email = logInEmail,
            password = logInPassword
        )
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            try {
                // Make the network call directly using the ApiService
                val response: Response<UserLoginResponseWithMessage> = getApiService().loginUser(user)
                if (response.isSuccessful) {
                    val result = response.body()
                    // Handle successful registration
                    if (result != null) {
                        // Show a success message or update UI accordingly
                        Log.d("SignUpViewModel", "Login successful: ${result.statusMessage}")
                        _loginStatus.value = result.statusMessage
                    } else {
                        // Handle the case where the body is null
                        Log.d("SignUpViewModel", "Unexpected response body")
                    }
                } else {
                    // Handle the error response
                    Log.d("SignUpViewModel", "Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("SignUpViewModel", "Error during login: ${e.localizedMessage}")
            }
        }
    }
}