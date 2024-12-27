package com.example.e_commerce_mobile.presentation.ui.screens.auth

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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
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
fun SignUpScreen(
    viewModel: UserAccountManagementViewModel = viewModel(),
    navController: NavController = NavController(context = LocalContext.current)
) {
    Scaffold(
        topBar = { HeadlineAppBar(title = "Sign Up", onNavigationClick = {navController.navigateUp()}) }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
                .padding(16.dp).verticalScroll(rememberScrollState()).imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            FormSignUp(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                firstName = viewModel.signUpFirstName,
                lastName = viewModel.signUpLastName,
                email = viewModel.signUpEmail,
                phoneNumber = viewModel.signUpPhoneNumber,
                password = viewModel.signUpPassword,
                onFirstNameChange = viewModel.onSignUpFirstNameChange,
                onLastNameChange = viewModel.onSignUpLastNameChange,
                onPhoneNumberChange = viewModel.onSignUpPhoneNumberChange,
                onEmailChange = viewModel.onSignUpEmailChange,
                onPasswordChange = viewModel.onSignUpPasswordChange)
            Row(
                modifier = Modifier.align(Alignment.End).clickable(onClick = {navController.navigate(
                    Screens.LogInScreen.route)}),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    fontSize = 15.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.to),
                    contentDescription = "to login Screen button",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            ElevatedButton(
                onClick = { viewModel.registerUser()},
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
                    "SIGN UP",
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Or sign up with social account",
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.size(width = 100.dp, height = 64.dp).shadow(elevation = 10.dp, shape = RoundedCornerShape(25.dp), spotColor = Color(0xFF9B9B9B)),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        // box design the sytem
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
                        modifier = Modifier.size(width = 100.dp, height = 64.dp).shadow(elevation = 10.dp, shape = RoundedCornerShape(25.dp), spotColor = Color(0xFF9B9B9B)),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        // card for google icon
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
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationClick: () -> Unit = {}
) {
    // tio app bar of hte  design the ystem
    LargeTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 34.sp,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold))),
            )
        },

        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp).clickable { onNavigationClick() }
            )
        },
        modifier = Modifier.height(140.dp),
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.White
        )
    )
}
// django sample ofthe  current vlaueo the   sampeof th  sam
// sample casting


@Composable
fun FormSignUp(modifier: Modifier = Modifier,
               firstName:String = "",
               lastName:String = "",
               email:String = "",
               phoneNumber:String = "",
               password:String = "",
               onFirstNameChange: (String) -> Unit = {},
               onLastNameChange: (String) -> Unit = {},
               onPhoneNumberChange: (String) -> Unit = {},
               onEmailChange: (String) -> Unit = {},
               onPasswordChange: (String) -> Unit = {}) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(15.dp)) {
        TextField(
            value = firstName,
            onValueChange = {onFirstNameChange(it)},
            label = {
                    Text(
                        "First Name",
                        color = Color(0xFF9B9B9B),
                        fontSize = 15.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                    )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
            value = lastName,
            onValueChange = {onLastNameChange(it)},
            label = {
                Text(
                    "Last Name",
                    color = Color(0xFF9B9B9B),
                    fontSize = 15.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
            value = email,
            onValueChange = {onEmailChange(it)},
            label = {
                    Text(
                        "Phone Number",
                        color = Color(0xFF9B9B9B),
                        fontSize = 15.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                    )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
            value = phoneNumber,
            onValueChange = {onPhoneNumberChange(it)},
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
                .height(70.dp)
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
                .height(70.dp)
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

@Preview(apiLevel = 35)
@Composable
private fun Preview() {
    SignUpScreen()

}