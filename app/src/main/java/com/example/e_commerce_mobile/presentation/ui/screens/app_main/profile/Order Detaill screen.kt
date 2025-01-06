package com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(modifier: Modifier = Modifier,
                       navController: NavController = NavController(context = LocalContext.current)) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Order Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(R.drawable.back_arrow), contentDescription = "Back")
                    }
                }
            )

        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {

        }
    }
}