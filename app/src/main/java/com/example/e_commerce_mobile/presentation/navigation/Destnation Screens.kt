package com.example.e_commerce_mobile.presentation.navigation

enum class Screens(val route: String) {
    Auth("Auth"),

    LogInScreen("LogInScreen"),
    SignUpScreen("SignUpScreen"),
    PasswordResetScreen("PasswordResetScreen"),

    Home("Home"),
    HomeMainScreen("HomeMainScreen"),

    Shop("Shop"),
    ProductBrowsingAndSearching("ProductBrowsingAndSearching"),
    CategoryAndSearchScreen("CategoryAndSearchScreen"),
    SubCategoryProductsOverviewScreen("SubCategoryProductsOverviewScreen"),
    ProductDisplayAndInformation("ProductDisplayAndReviewScreen"),
    ProductDetailScreen("ProductDetailScreen"),
    Product3DModelScreen("Product3DModelScreen"),

    Bag("Bag"),
    BagMainScreen("BagMainScreen"),

    Favorites("Favorites"),
    FavoritesMainScreen("FavoritesMainScreen"),

    Profile("Profile"),
    ProfileMainScreen("ProfileMainScreen"),
    MyOrdersScreen("MyOrdersScreen"),
    OrderDetailsScreen("OrderDetailsScreen");



}
