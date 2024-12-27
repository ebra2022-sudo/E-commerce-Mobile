package com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_mobile.screens.auth.UserAccountManagementViewModel

@Composable
fun ProfileMainScreen(modifier: Modifier = Modifier, viewModel: UserAccountManagementViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Green), contentAlignment = Alignment.Center) {
        Text("This is Profile Main Screen")
    }
    
}