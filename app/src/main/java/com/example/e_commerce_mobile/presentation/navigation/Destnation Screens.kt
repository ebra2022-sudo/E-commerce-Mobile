package com.example.e_commerce_mobile.presentation.navigation

enum class Screens(val route: String) {
    Auth("Auth"),

    LogInScreen("LogInScreen"),
    SignUpScreen("SignUpScreen"),
    PasswordResetScreen("PasswordResetScreen"),

    Home("Home"),
    HomeMainScreen("HomeMainScreen"),

    Shop("Shop"),
    ShopMainScreen("ShopMainScreen"),
    ProductBrowsingAndSearching("ProductBrowsingAndSearching"),
    CategoryAndSearchScreen("CategoryAndSearchScreen"),
    SubCategoryProductsOverviewScreen("SubCategoryProductsOverviewScreen/{mainCategory}/{subCategoryTitle}"),
    ProductDisplayAndInformation("ProductDisplayAndReviewScreen"),

    Bag("Bag"),
    BagMainScreen("BagMainScreen"),

    Favorites("Favorites"),
    FavoritesMainScreen("FavoritesMainScreen"),
    Profile("Profile"),
    ProfileMainScreen("ProfileMainScreen");

    /**
     * Helper function to build the route with arguments
     */
    fun withArgs(vararg args: String): String {
        return route + args.joinToString(separator = "/", prefix = "/")
    }
}
