package com.example.e_commerce_mobile.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel: UserAccountManagementViewModel = viewModel(),
    navController: NavController = NavController(context = LocalContext.current)
) {
    Scaffold(
        topBar = { HeadlineAppBar(title = "Login",
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
            Spacer(modifier = Modifier.height(50.dp))
            FormLogIn(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                email = viewModel.logInEmail,
                password = viewModel.logInPassword,
                onEmailChange = viewModel.onLogInEmailChange,
                onPasswordChange = viewModel.onLogInPasswordChange)
            Row(
                modifier = Modifier.align(Alignment.End).clickable(onClick = {navController.navigate(Screens.PasswordResetScreen.route)}),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Forget your password?",
                    fontSize = 15.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
                Icon(
                    painter = painterResource(R.drawable.to),
                    contentDescription = "to password reset Screen button",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            ElevatedButton(
                onClick = {
                    if(viewModel.logInEmail == "ebrahimmuhammed479@gmail.com" && viewModel.logInPassword == "@#0214Mu")
                        navController.navigate(Screens.Home.route)
                },
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
                    "LOGIN",
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
            }
            Spacer(modifier = Modifier.height(200.dp))
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Or login with social account",
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(width = 100.dp, height = 64.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(25.dp),
                                spotColor = Color(0xFF9B9B9B)
                            ),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.google_icon),
                                contentDescription = "google icon",
                                tint = Color.Unspecified
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .size(width = 100.dp, height = 64.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(25.dp),
                                spotColor = Color(0xFF9B9B9B)
                            ),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.facebook_icon),
                                contentDescription = "google icon",
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .size(width = 100.dp, height = 64.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(25.dp),
                                spotColor = Color(0xFF9B9B9B)
                            ),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.x_icon),
                                contentDescription = "google icon",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
                TextButton(onClick = {navController.navigate(Screens.SignUpScreen.route)}) {
                    Text("Don't have an account? Sign Up")
                }
            }
        }
    }
}


@Composable
fun FormLogIn(modifier: Modifier = Modifier, email:String = "", password:String = "", onEmailChange: (String) -> Unit = {}, onPasswordChange: (String) -> Unit = {}) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(15.dp)) {
        TextField(
            value = email,
            onValueChange = {onEmailChange(it)},
            label = {
                Text(
                    "Email",
                    color = Color(0xFF9B9B9B),
                    fontSize = 15.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(10.dp), spotColor = Color(0xFF9B9B9B)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp)
        )
        TextField(
            value = password,
            onValueChange = {onPasswordChange(it)},
            label = {
                Text(
                    "Password",
                    color = Color(0xFF9B9B9B),
                    fontSize = 15.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(10.dp), spotColor = Color(0xFF9B9B9B)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }

}


@Preview
@Composable
private fun Preview() {
    LogInScreen()
}
