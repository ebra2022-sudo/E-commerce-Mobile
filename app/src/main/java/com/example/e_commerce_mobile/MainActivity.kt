package com.example.e_commerce_mobile

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
import com.example.e_commerce_mobile.screens.ScreenContainer
import com.example.e_commerce_mobile.screens.app_main.home.HomeMainScreen
import com.example.e_commerce_mobile.screens.app_main.shop.product_browsing_and_searching.CategoryAndSearchScreen
import com.example.e_commerce_mobile.ui.theme.EcommerceMobileTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val systemUiController = rememberSystemUiController()


            // Update the status bar color dynamically
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.White
                )
            }

            ScreenContainer()
                //HomeMainScreen()
            //CategoryAndSearchScreen()

        }
    }
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