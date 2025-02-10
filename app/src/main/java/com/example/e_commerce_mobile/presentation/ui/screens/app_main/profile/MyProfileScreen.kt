package com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_mobile.presentation.ui.screens.auth.HeadlineAppBar
import com.example.e_commerce_mobile.presentation.viewmodel.UserAccountManagementViewModel
import com.example.e_commerce_mobile.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(modifier: Modifier = Modifier,
                    navController: NavController = NavController(LocalContext.current),
                    viewModel: UserAccountManagementViewModel = hiltViewModel(),
) {
    var expanded by remember { mutableStateOf(false) } // Controls dropdown menu visibility

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        fontSize = 34.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold)))
                    )
                },
                actions = {
                    // Menu icon to trigger the dropdown
                    IconButton(onClick = { expanded = !expanded }, modifier = Modifier) {
                        Icon(
                            painter = painterResource(id = R.drawable.dots_vertical), // Replace with your menu icon
                            contentDescription = "Menu"
                        )
                    }
                    // Dropdown Menu
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = { Text("Log out") }, onClick = {viewModel.logoutUser() })
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            ProfileAvatarWithNameAndEmail(
                avatarUrl = "",
                name = "Muhammed Ebrahim",
                email = "ebrahim@gmail.com")
            Spacer(modifier = Modifier.height(50.dp))
            val profileSections = listOf(
                Section("My Orders", "My Order overview"),
                Section("Shipping Addresses", "Shipping overview"),
                Section("Payment Methods", "Payment overview"),
                Section("Promo codes", "promo codes overview"),
                Section("My Reviews", "Reviews overview"),
                Section("Settings", "Settings overview")
            )
            profileSections.forEach {
                ProfileSectionItem(sectionName = it.name, sectionOverview = it.overview, onItemClick = {navController.navigate(it.route)})
                Spacer(modifier = Modifier.height(32.dp))
            }
            // design the  sa te of the

        }
    }
}

data class Section(val name: String,
                   val overview: String,
                   val route: String = Screens.MyOrdersScreen.route)

@Composable
fun ProfileAvatarWithNameAndEmail(modifier: Modifier = Modifier,
                                  avatarUrl: String,
                                  name: String,
                                  email: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.Top){
        Image(modifier = Modifier
            .size(60.dp)
            .clip(CircleShape),
            painter = painterResource(R.drawable.ava),
            contentScale = ContentScale.FillBounds,
            contentDescription = "avatar")
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF222222),
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))))
            Text(text = email,
                fontSize = 14.sp,
                color = Color(0xFF9B9B9B))
        }



    }
    
}


@Composable
fun ProfileSectionItem(modifier: Modifier = Modifier,
                       sectionName: String,
                       sectionOverview: String,
                       onItemClick: () -> Unit = {}) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable(onClick = onItemClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = sectionName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF222222),
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = sectionOverview,
                fontSize = 14.sp,
                color = Color(0xFF9B9B9B)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = "navigate to $sectionName"
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyProfileScreen()
    // design the state
}