package com.example.e_commerce_mobile.presentation.ui.screens.app_main.favorites

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.navigation.Screens
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.ProductOverviewCardHorizontal
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.ProductOverviewCardVertical
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.VerticalLazyGridWithKItemsPerScreen
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information.ControlTextButtons
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesMainScreen(modifier: Modifier = Modifier,
                        viewModel: ProductViewModel = hiltViewModel(),
                        navController: NavController = NavController(LocalContext.current)) {
    viewModel.fetchLikedProducts()
    val likedProducts = viewModel.likedProducts.collectAsState().value
    Log.d("FavoriteMainScreen", likedProducts.toString())
    var gridView by rememberSaveable { mutableStateOf(false) }
    val userId = viewModel.userId.collectAsState().value?.toInt()?:-1
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val currentLikedProduct = viewModel.currentLikedProduct.collectAsState().value


    Scaffold(
        topBar = {
            MediumTopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = "My favorites", modifier = Modifier.fillMaxWidth(),
                        textAlign = if (scrollBehavior.state.collapsedFraction >= 0.75f) TextAlign.Center else TextAlign.Start,
                        fontSize = 30.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold)))

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        //navController.navigateUp()
                    }) {
                        Icon(painter = painterResource(R.drawable.back), contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White,

                    )
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            if (likedProducts.isEmpty()) {
                Text(text = "No favorites yet", modifier = Modifier.align(Alignment.Center))
            } else {
                val productOverviews = likedProducts.sortedBy { it.name }.map{ product ->
                    if(currentLikedProduct?.id == product.id) {
                        product.copy(likedBy = currentLikedProduct.likedBy)
                    }
                    viewModel.getCurrentSubSubCategory(product.subSubCategory)
                    val currentSubSubCategory = viewModel.currentSubSubCategory.collectAsState().value
                    @Composable { width: Int ->
                        viewModel.getCurrentSubSubCategory(product.subSubCategory)

                        if (gridView) {
                            ProductOverviewCardHorizontal(
                                productName = product.name,
                                subSubCategory = currentSubSubCategory?.name?:"Unknown",
                                isNew = false,
                                isLiked = product.likedBy.contains(userId),
                                discountPercent = product.discount.toInt(),
                                price = product.price.toDouble(),
                                rating = product.userRating.toFloat(),
                                productImage = product.productImageUrl?:"",
                                onFavorite = {
                                    viewModel.onLike(product.id)
                                }
                            ) {
                                navController.navigate(Screens.ProductDetailScreen.withArgs(product.id.toString()))
                            }
                        }
                        else {
                            ProductOverviewCardVertical(
                                productName = product.name,
                                subSubCategory = currentSubSubCategory?.name?:"Unknown",
                                width = width,
                                isLiked = product.likedBy.contains(userId),
                                isNew = false,
                                discountPercent =product.discount.toInt(),
                                price = product.price.toDouble(),
                                rating =product.userRating.toFloat(),
                                productImage = product.productImageUrl?:"",
                                onFavorite = {
                                    viewModel.onLike(product.id)
                                }
                            ){
                                navController.navigate(Screens.ProductDetailScreen.withArgs(product.id.toString()))
                            }
                        }
                    }
                }
                Column {
                    ControlTextButtons(
                        currentSortType = "Price: lowest to high",
                        currentViewIcon = if (gridView) R.drawable.detail_view_icon else R.drawable.grid_view_icon,
                        onCollapseClick = {
                            gridView = !gridView
                        }

                    )
                    VerticalLazyGridWithKItemsPerScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        k = if (gridView) 1 else if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3,
                        spacer = 10,
                        items = productOverviews,
                        nestedScrollConnection = scrollBehavior.nestedScrollConnection
                    )
                }
            }
        }
    }
}



@Preview
@Composable
private fun Preview() {
    FavoritesMainScreen()
    
}