package com.example.e_commerce_mobile.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.viewmodel.UserAccountManagementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetScreen(
    viewModel: UserAccountManagementViewModel = hiltViewModel(),
    navController: NavController = NavController(context = LocalContext.current)
) {
    Scaffold(
        topBar = { HeadlineAppBar(title = "Forget Password",
            onNavigationClick = {navController.navigateUp()}) }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Text(text = "Please, enter your email address. You will receive a link to create a new password via email.",
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)), fontSize = 15.sp))
            TextField(
                value = viewModel.passwordResetEmail,
                onValueChange = {viewModel.onPasswordResetEmailChange(it)},
                label = {
                    Text(
                        "Email",
                        color = Color(0xFF9B9B9B),
                        fontSize = 15.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                    )

                },
                modifier = Modifier
                    .fillMaxWidth().padding(vertical = 16.dp)
                    .height(74.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp),
                        spotColor = Color(0xFF9B9B9B)
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))
            ElevatedButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDB3022),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    "SEND",
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
            }

        }
    }
}


@Preview
@Composable
private fun Preview() {
    PasswordResetScreen()
}