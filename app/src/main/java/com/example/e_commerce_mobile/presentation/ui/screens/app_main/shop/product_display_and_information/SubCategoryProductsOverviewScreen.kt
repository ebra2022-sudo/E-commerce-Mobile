package com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.navigation.Screens
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.ProductOverviewCardHorizontal
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.ProductOverviewCardVertical
import com.example.e_commerce_mobile.presentation.ui.screens.app_main.home.VerticalLazyGridWithKItemsPerScreen
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SubCategoryProductsOverviewScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    subCategoryId: Int,
    navController: NavController = NavController(LocalContext.current),

) {
    viewModel.fetchSubSubCategories(subCategoryId)
    viewModel.getCurrentSubCategory(subCategoryId)
    viewModel.fetchProducts(subCategoryId)
    Log.d("SubCategoryProductsOverviewScreen", "subCategoryId: $subCategoryId")
    Log.d("SubCategoryProductsOverviewScreen", "subSubCategories: ${viewModel.subSubCategories.collectAsState().value}")
    val subSubCategories = viewModel.subSubCategories.collectAsState().value
    val subCategory = viewModel.currentSubCategory.collectAsState().value
    val subSubCategory = viewModel.currentSubSubCategory.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currentProducts = viewModel.currentProducts.collectAsState().value
    Scaffold(
        topBar = {
            MediumTopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = subCategory?.name?:"Unknown", modifier = Modifier.fillMaxWidth(),
                        textAlign = if (scrollBehavior.state.collapsedFraction >= 0.75f) TextAlign.Center else TextAlign.Start,
                        fontSize = 30.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold)))

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
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
    ) { paddingValues ->
        val adjustedPadding = PaddingValues(
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            top = paddingValues.calculateTopPadding(),// Override the top padding to zero
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = 0.dp
        )
        val pagerState = rememberPagerState { subSubCategories.size }
        val coroutineScope = rememberCoroutineScope()
        Log.d("mainCategory", (subCategory?.mainCategoryId?:"Unknown").toString())
        Log.d("subCategory", subCategory?.name?:"Unknown")
        Log.d("subSubCategoriesToCorrespondingProducts length", subSubCategories.size.toString())
        Log.d("allProducts", subSubCategories.size.toString())
        var gridView by rememberSaveable { mutableStateOf(false) }

        Column(modifier = Modifier.padding(adjustedPadding).background(Color.White)) {
            // Tab Row
            if (!(LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE && scrollBehavior.state.collapsedFraction >= 0.6f)) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.White,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        // Custom color for the tab indicator
                    },
                    divider = { Text("|")}
                ) {

                    subSubCategories.map { it.name }.forEachIndexed { index, subSubCategory ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        index,
                                        animationSpec = tween(durationMillis = 1000)
                                    )
                                }
                            },
                            text = {
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(10.dp))
                                        .background(
                                            if (pagerState.currentPage == index) Color(0xFFDB3022) else Color(
                                                0xFF222222
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                        text = subSubCategories[index].name,
                                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.W300,
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                                    )
                                }
                            },
                        )
                    }
                }
                ControlTextButtons(
                    currentSortType = "Price: lowest to high",
                    currentViewIcon = if (gridView) R.drawable.detail_view_icon else R.drawable.grid_view_icon,
                    onCollapseClick = {
                        gridView = !gridView
                    }
                )
            }

            HorizontalPager(
                state = pagerState,
                // Take up remaining space
            ) { page ->
                val productOverviews = currentProducts.map{ product ->

                    @Composable { width: Int ->
                        viewModel.getCurrentSubSubCategory(product.subSubCategory)
                        var isLiked by rememberSaveable { mutableStateOf(product.isLiked) }
                        if (gridView) {
                            ProductOverviewCardHorizontal(
                                productName = product.name,
                                subSubCategory = subSubCategory?.name?:"Unknown",
                                isNew = false,
                                isLiked = isLiked,
                                discountPercent = product.discount.toInt(),
                                price = product.price.toDouble(),
                                rating = product.userRating.toFloat(),
                                productImage = product.productImageUrl?:"",
                                onFavorite = {
                                    viewModel.onLike(product.id)
                                    isLiked = !isLiked
                                }
                            ) {
                                navController.navigate(Screens.ProductDetailScreen.withArgs(product.id.toString()))
                            }
                        }
                        else {
                            ProductOverviewCardVertical(
                                productName = product.name,
                                subSubCategory = subSubCategory?.name?:"Unknown",
                                width = width,
                                isLiked = isLiked,
                                isNew = false,
                                discountPercent =product.discount.toInt(),
                                price = product.price.toDouble(),
                                rating =product.userRating.toFloat(),
                                productImage = product.productImageUrl?:"",
                                onFavorite = {
                                    viewModel.onLike(product.id)
                                    isLiked = !isLiked
                                }
                            ){
                                navController.navigate(Screens.ProductDetailScreen.withArgs(product.id.toString()))
                            }
                        }
                    }
                }
                VerticalLazyGridWithKItemsPerScreen(modifier = Modifier
                    .fillMaxSize().padding(16.dp), nestedScrollConnection = scrollBehavior.nestedScrollConnection, k = if (gridView) 1 else if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3,
                    spacer = 10,
                    items = productOverviews
                )
            }
        }
    }
}


@Composable
fun ControlTextButtons(currentSortType: String = "Price: lowest to high", currentViewIcon: Int = R.drawable.grid_view_icon, onFilterClick: () -> Unit = {}, onSortClick: () -> Unit = {}, onCollapseClick: () -> Unit = {}) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Row(modifier = Modifier.clickable(onClick = onFilterClick), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(painter = painterResource(R.drawable.filter_icon), contentDescription = "Filter icon")
                Text(text = "Filter")
            }
            Row(modifier = Modifier.clickable(onClick = onSortClick), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(painter = painterResource(R.drawable.sort_icon), contentDescription = "Sort icon")
                Text(text = currentSortType)
            }
            Row(modifier = Modifier.clickable(onClick = onCollapseClick)) {
                Icon(painter = painterResource(currentViewIcon), contentDescription = "Collapse icon")
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0xFF222222), modifier = Modifier.fillMaxWidth().shadow(elevation = 5.dp))
    }
}













