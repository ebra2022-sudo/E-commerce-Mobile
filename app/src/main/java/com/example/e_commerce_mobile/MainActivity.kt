package com.example.e_commerce_mobile

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.e_commerce_mobile.presentation.navigation.ScreenContainer
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.HomeMainScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching.CategoryAndSearchScreen
import com.example.e_commerce_mobile.presentation.ui.theme.EcommerceMobileTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenContainer()
        }
    }
}

@HiltAndroidApp
class MyApplication : Application() {

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcommerceMobileTheme {
        Greeting("Android")
    }
}