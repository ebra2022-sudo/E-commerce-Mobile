package com.example.e_commerce_mobile.presentation.navigation


import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.bag.BagMainScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.favorites.FavoritesMainScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.HomeMainScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile.MyProfileScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching.CategoryAndSearchScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information.Product3DPreviewScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information.ProductDetailScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information.SubCategoryProductsOverviewScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.LogInScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.PasswordResetScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.SignUpScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenContainer(modifier: Modifier = Modifier, lifecycle: Lifecycle, context: Context) {
    val navController = rememberNavController()
    val isAuthenticated = false // Replace with actual authentication logic
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    Log.d("CurrentRoute", currentRoute.toString())
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }


    // Status bar height

    Scaffold(
        bottomBar = {
            if (shouldShowBottomNavigation(currentRoute)) {
                BottomNavigationBar(navController = navController, selectedIndex = selectedIndex, onItemSelected = {selectedIndex = it})
            }
        }
    ) {paddingValues ->
        NavHost(
            modifier = modifier.padding(paddingValues),
            navController = navController,
            startDestination = if (isAuthenticated) Screens.Home.route else Screens.Auth.route,
            enterTransition = {slideInHorizontally { it }},
            exitTransition = {slideOutHorizontally{ -it}}
        ) {
            // Authentication flow
            navigation(startDestination = Screens.LogInScreen.route, route = Screens.Auth.route) {
                composable(Screens.LogInScreen.route) { LogInScreen(navController = navController) }
                composable(Screens.SignUpScreen.route) { SignUpScreen(navController = navController) }
                composable(Screens.PasswordResetScreen.route) { PasswordResetScreen(navController = navController) }
            }
            // Main App flow
            navigation(startDestination = Screens.HomeMainScreen.route, route = Screens.Home.route) {
                composable(
                    Screens.HomeMainScreen.route, enterTransition = {slideInHorizontally { it }},
                    exitTransition = {slideOutHorizontally{ -it}}) { HomeMainScreen(navController = navController) }
            }
            navigation(startDestination = Screens.ProductBrowsingAndSearching.route, route = Screens.Shop.route) {
                navigation(startDestination = Screens.CategoryAndSearchScreen.route, route = Screens.ProductBrowsingAndSearching.route) {
                    composable(Screens.CategoryAndSearchScreen.route) {
                        CategoryAndSearchScreen(
                            navController = navController
                        )
                    }
                }
                navigation(startDestination = Screens.SubCategoryProductsOverviewScreen.route, route = Screens.ProductDisplayAndInformation.route) {
                    composable(
                        route = Screens.SubCategoryProductsOverviewScreen.withArgs("{subCategoryId}"),
                        arguments = listOf(
                            navArgument("subCategoryId") { type = NavType.StringType }
                        )

                    ) { backStackEntry ->
                        // Retrieve arguments safely
                        val subCategoryId = backStackEntry.arguments?.getString("subCategoryId") ?: "12"


                        // Call the screen composable
                        SubCategoryProductsOverviewScreen(
                            navController = navController,
                            subCategoryId = subCategoryId.toInt()
                        )
                    }
                    composable(Screens.ProductDetailScreen.withArgs("{productId}"),
                        arguments = listOf(
                            navArgument("productId") { type = NavType.StringType }
                        )){
                        val productId = it.arguments?.getString("productId") ?: "3"
                        ProductDetailScreen(navController = navController, productId = productId.toInt())
                    }
                    composable(Screens.Product3DModelScreen.withArgs("{modelUrl}"),
                        arguments = listOf(
                            navArgument("modelUrl") { type = NavType.StringType }
                        )) {
                        val modelUrl = it.arguments?.getString("modelUrl") ?: ""
                        Product3DPreviewScreen(url = modelUrl, navController = navController, lifecycle = lifecycle, context = context) }
                }
            }
            // sample of the  ste of he  current valeu of the  sate of the design state of then
            navigation(startDestination = Screens.BagMainScreen.route, route = Screens.Bag.route) {
                composable(Screens.BagMainScreen.route) { BagMainScreen() }
            }
            navigation(startDestination = Screens.FavoritesMainScreen.route, route = Screens.Favorites.route) {
                composable(Screens.FavoritesMainScreen.route) { FavoritesMainScreen() }
            }
            navigation(startDestination = Screens.ProfileMainScreen.route, route = Screens.Profile.route) {
                composable(Screens.ProfileMainScreen.route) { MyProfileScreen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController, selectedIndex: Int, onItemSelected: (Int) -> Unit = {}) {

    val items = listOf(Screens.Home.route, Screens.Shop.route, Screens.Bag.route, Screens.Favorites.route, Screens.Profile.route)
    val unSelectedIconResources = listOf(R.drawable.outlined_home, R.drawable.outlined_shop, R.drawable.outlined_bag, R.drawable.outlined_favorite, R.drawable.outlined_profile)
    val selectedIconResources = listOf(R.drawable.filled_home, R.drawable.filled_shop, R.drawable.filled_bag, R.drawable.filled_favorite, R.drawable.filled_profile)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route


    NavigationBar(
        modifier = Modifier
            .shadow(
                elevation = 40.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                clip = false, // Ensures the shadow is drawn outside the bounds
                spotColor = Color(0xFF9B9B9B)
            )
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.White,
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (selectedIndex == index) selectedIconResources[index] else unSelectedIconResources[index]),
                        contentDescription = screen,
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = screen,
                        color = if (selectedIndex == index) Color(0xFFDB3022) else Color.Unspecified
                    )
                },
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                    if (currentRoute != screen) {
                        navController.navigate(screen) {
                            // Pop up to the root destination of the graph to avoid building up a large stack of destinations
                            // on the back stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedTextColor = Color(0xFFDB3022),
                    unselectedIconColor = Color.Unspecified
                )
            )
        }
    }
}
fun shouldShowBottomNavigation(currentRoute: String?): Boolean {
    return currentRoute in listOf(
        Screens.HomeMainScreen.route,
        Screens.CategoryAndSearchScreen.route,
        Screens.SubCategoryProductsOverviewScreen.withArgs("{subCategoryId}"),
        Screens.BagMainScreen.route,
        Screens.FavoritesMainScreen.route,
        Screens.ProfileMainScreen.route
    )
}
// design sample case




@Preview
@Composable
private fun Preview() {
    //ScreenContainer()
    
}

