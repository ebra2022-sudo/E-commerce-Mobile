package com.example.e_commerce_mobile.presentation.navigation


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
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile.ProfileMainScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching.CategoryAndSearchScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching.Product
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching.SubcategoryItem
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information.SubCategoryProductsOverviewScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.LogInScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.PasswordResetScreen
import com.example.e_commerce_mobile.presentation.ui.screens.auth.SignUpScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenContainer(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val isAuthenticated = false // Replace with actual authentication logic
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    Log.d("currentRoute", currentRoute.toString())
    val allProducts = listOf(
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Jacket",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Jacket",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Jacket",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Jacket",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Suit",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Suit",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Suit",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Suit",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Trousers",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Trousers",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Trousers",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Trousers",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Men", imageId = R.drawable.men),
            subSubCategory = "Trousers",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Women", imageId = R.drawable.women),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Kids", imageId = R.drawable.kids),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Traditional", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Clothing",
            subCategory = SubcategoryItem(title = "Sportswear", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Smart Phones", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Smart Watches", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Smart Glasses", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Tablet PCs", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(
                title = "Earbuds and Headsets",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Ar/Vr Devices", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Smart Devices",
            subCategory = SubcategoryItem(title = "Medical Devices", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Home Appliances",
            subCategory = SubcategoryItem(title = "kitchens", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Home Appliances",
            subCategory = SubcategoryItem(
                title = "Cleaning Appliances",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Home Appliances",
            subCategory = SubcategoryItem(title = "Entertainment", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Home Appliances",
            subCategory = SubcategoryItem(title = "Home Decorator", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Home Appliances",
            subCategory = SubcategoryItem(title = "Furniture", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Foot wears",
            subCategory = SubcategoryItem(title = "Casual Shoes", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Foot wears",
            subCategory = SubcategoryItem(title = "Formal Shoes", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Foot wears",
            subCategory = SubcategoryItem(title = "Sports Shoes", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Foot wears",
            subCategory = SubcategoryItem(title = "sandals", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cosmetics",
            subCategory = SubcategoryItem(title = "Skincare", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cosmetics",
            subCategory = SubcategoryItem(title = "Haircare", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cosmetics",
            subCategory = SubcategoryItem(title = "Makeup", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cosmetics",
            subCategory = SubcategoryItem(title = "Fragrances", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cars",
            subCategory = SubcategoryItem(title = "Sport Cars", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cars",
            subCategory = SubcategoryItem(title = "Luxury Cars", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cars",
            subCategory = SubcategoryItem(
                title = "Off-Road and Adventure Vehicles",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cars",
            subCategory = SubcategoryItem(
                title = "Eco-Friendly Vehicles",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Cars",
            subCategory = SubcategoryItem(title = "Trucks", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Books",
            subCategory = SubcategoryItem(title = "Fiction", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Books",
            subCategory = SubcategoryItem(title = "Non-Fiction", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Books",
            subCategory = SubcategoryItem(title = "Educational", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Books",
            subCategory = SubcategoryItem(title = "Religious", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Books",
            subCategory = SubcategoryItem(title = "Comics", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Construction", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Agriculture", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Industrial", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Mining", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),

        // product os the  currtnt  vaue of the selected category
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Textile", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Food Processing", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(title = "Automotive", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Machinery",
            subCategory = SubcategoryItem(
                title = "Printing and Publishing",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Accessories",
            subCategory = SubcategoryItem(
                title = "Fashion Accessories",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Accessories",
            subCategory = SubcategoryItem(title = "Tech Accessories", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Accessories",
            subCategory = SubcategoryItem(
                title = "Automotive Accessories",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Accessories",
            subCategory = SubcategoryItem(title = "Home Accessories", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Accessories",
            subCategory = SubcategoryItem(
                title = "Gaming Accessories",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(title = "Office Supplies", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(
                title = "Gift and Souvenirs",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(
                title = "Outdoor and Camping",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(
                title = "Health and Wellness",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(
                title = "Travel and Leisure",
                imageId = R.drawable.men
            ),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        ),
        Product(
            mainCategory = "Others",
            subCategory = SubcategoryItem(title = "Toys and Hobbies", imageId = R.drawable.men),
            subSubCategory = "T-Shirt",
            title = "Men's T-Shirt",
            imageId = R.drawable.men,
            price = "$19.99"
        )
    )

    // Status bar height
    Scaffold(
        bottomBar = {
            if (shouldShowBottomNavigation(currentRoute)) {
                BottomNavigationBar(navController = navController)
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
                            navController = navController,
                            allProducts = allProducts
                        )
                    }
                }
                navigation(startDestination = Screens.SubCategoryProductsOverviewScreen.route, route = Screens.ProductDisplayAndInformation.route) {
                    composable(
                        route = Screens.SubCategoryProductsOverviewScreen.withArgs("{mainCategory}/{subCategoryTitle}"),
                        arguments = listOf(
                            navArgument("mainCategory") { type = NavType.StringType },
                            navArgument("subCategoryTitle") { type = NavType.StringType }
                        )

                    ) { backStackEntry ->
                        // Retrieve arguments safely
                        val subCategoryTitle = backStackEntry.arguments?.getString("subCategoryTitle") ?: "Unknown"
                        val mainCategory = backStackEntry.arguments?.getString("mainCategory") ?: "Unknown"

                        // Call the screen composable
                        SubCategoryProductsOverviewScreen(
                            navController = navController,
                            mainCategory = mainCategory,
                            subCategory = subCategoryTitle,
                            allProducts = allProducts
                        )
                    }
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
                composable(Screens.ProfileMainScreen.route) { ProfileMainScreen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(Screens.Home.route, Screens.Shop.route, Screens.Bag.route, Screens.Favorites.route, Screens.Profile.route)
    val unSelectedIconResources = listOf(R.drawable.outlined_home, R.drawable.outlined_shop, R.drawable.outlined_bag, R.drawable.outlined_favorite, R.drawable.outlined_profile)
    val selectedIconResources = listOf(R.drawable.filled_home, R.drawable.filled_shop, R.drawable.filled_bag, R.drawable.filled_favorite, R.drawable.filled_profile)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Log.d("CurrentRoute", currentRoute.toString())

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
                        painter = painterResource(id = if (selectedItem == index) selectedIconResources[index] else unSelectedIconResources[index]),
                        contentDescription = screen,
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = screen,
                        color = if (selectedItem == index) Color(0xFFDB3022) else Color.Unspecified
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
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
        Screens.SubCategoryProductsOverviewScreen.withArgs("{mainCategory}/{subCategoryTitle}"),
        Screens.BagMainScreen.route,
        Screens.FavoritesMainScreen.route,
        Screens.ProfileMainScreen.route
    )
}
// design sample case




@Preview
@Composable
private fun Preview() {
    ScreenContainer()
    
}

