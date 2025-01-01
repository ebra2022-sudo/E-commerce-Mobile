package com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_browsing_and_searching

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.data.local.MainCategory
import com.example.e_commerce_mobile.presentation.navigation.Screens
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryAndSearchScreen(navController: NavController = NavController(context = LocalContext.current), viewModel: ProductViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Categories",
                        fontSize = 22.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "back button"
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "search button",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                },
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
        MainCategoryNavigation(
            modifier = Modifier
                .padding(adjustedPadding)
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController
        )
    }
}


@Composable
fun SubcategoryAdCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDB3022),
            contentColor = Color.White
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "SUMMER SALES",
                    fontSize = 24.sp,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))),
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = "Up to 50% Off")
            }
        }
    }
}


data class Product(
    val mainCategory: String,
    val subCategory: SubcategoryItem = SubcategoryItem("", R.drawable.kids),
    val subSubCategory: String,
    val title: String,
    val price: String,
    val imageId: Int
)


@Composable
fun MainCategoryNavigation(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel,
    navController: NavController
) {
    val mainCategories = viewModel.mainCategories.collectAsState().value
    Log.d("MainCategoryNavigation", "mainCategories: $mainCategories")
    val pagerState = rememberPagerState { mainCategories.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        // Tab Row
        if (mainCategories.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 5.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color(0xFFDB3022) // Custom color for the tab indicator
                    )
                }
            ) {
                mainCategories.map { it.name }.forEachIndexed { index, mainCategory ->
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
                                text = mainCategory,
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.W300,
                                fontSize = 18.sp,
                                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                            )
                        },
                        selectedContentColor = Color(0xFF222222),
                    )
                }
            }

            // Horizontal Pager for swiping between tabs
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f) // Take up remaining space
            ) { page ->
                viewModel.fetchSubCategories(mainCategories[pagerState.currentPage].id)
                SubcategoryContentScreen(viewModel = viewModel, navController = navController)
            }
        }

        }

}


data class SubcategoryItem(
    val title: String,
    @DrawableRes val imageId: Int,
)


@Composable
fun SubcategoryContentScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
    viewModel: ProductViewModel,
    navController: NavController,
    subCategoryAd: @Composable () -> Unit = { SubcategoryAdCard() }
) {
    val subCategories = viewModel.subCategories.collectAsState().value

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Spacer(Modifier.height(16.dp))
            subCategoryAd()
        }
        items(subCategories) { subCategory ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable(onClick = {
                        val subCategoryId = subCategory.id
                        navController.navigate(Screens.SubCategoryProductsOverviewScreen.withArgs(
                            subCategoryId.toString()
                        ))
                        Log.d("SubcategoryContentScreen", "Subcategory clicked: $subCategory")
                    }),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = subCategory.name,
                        modifier = Modifier.fillMaxWidth(0.5f),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))),
                        fontWeight = FontWeight.W600,
                        fontSize = 18.sp
                    )

                    Image(
                        painter = rememberAsyncImagePainter(model = subCategory.image),
                        contentDescription = "subcategory image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}










@Preview
@Composable
private fun Preview() {
  //  CategoryAndSearchScreen()


}