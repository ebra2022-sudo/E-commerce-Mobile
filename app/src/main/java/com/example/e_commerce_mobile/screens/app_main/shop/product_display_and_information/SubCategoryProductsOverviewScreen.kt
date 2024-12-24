package com.example.e_commerce_mobile.screens.app_main.shop.product_display_and_information

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.screens.app_main.home.ProductOverviewCard
import com.example.e_commerce_mobile.screens.app_main.shop.product_browsing_and_searching.Product
import com.example.e_commerce_mobile.screens.app_main.shop.product_browsing_and_searching.SubcategoryContentScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SubCategoryProductsOverviewScreen(
    modifier: Modifier = Modifier,
    mainCategory: String,
    subCategory: String,
    navController: NavController = NavController(LocalContext.current),
    allProducts: List<Product> = emptyList()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = subCategory, modifier = Modifier.fillMaxWidth(),
                        textAlign = if (scrollBehavior.state.collapsedFraction == 1f) TextAlign.Center else TextAlign.Start
                    )
                },
                navigationIcon = {
                    Icon(painter = painterResource(R.drawable.back), contentDescription = "Back")
                },
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Search"
                    )

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
        val subSubCategoriesToCorrespondingProducts =
            allProducts.filter { it.mainCategory == mainCategory && it.subCategory.title == subCategory }
                .groupBy { it.subSubCategory }
        val pagerState = rememberPagerState { subSubCategoriesToCorrespondingProducts.size }
        val coroutineScope = rememberCoroutineScope()
        Log.d("mainCategory", mainCategory)
        Log.d("subCategory", subCategory)
        Log.d("subSubCategoriesToCorrespondingProducts length", subSubCategoriesToCorrespondingProducts.size.toString())
        Log.d("allProducts", allProducts.size.toString())

        Column(modifier = Modifier.padding(adjustedPadding)) {
            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 5.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color(0xFFDB3022) // Custom color for the tab indicator
                    )
                }
            ) {
                subSubCategoriesToCorrespondingProducts.keys.forEachIndexed { index, subSubCategory ->
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
                            Text(
                                text = subSubCategory,
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.W300,
                                fontSize = 18.sp,
                                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                            )
                        },
                        selectedContentColor = Color(0xFF222222),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Filter")
                Text(text = "Sort")
                Text(text = "Collapse")
            }

            // Horizontal Pager for swiping between tabs
            HorizontalPager(
                state = pagerState,
                // Take up remaining space
            ) { page ->
                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    items(subSubCategoriesToCorrespondingProducts.values.toList()[page]) { product ->
                        ProductOverviewCard(
                            width = 150
                        )
                    }
                }
            }
        }
    }
}












