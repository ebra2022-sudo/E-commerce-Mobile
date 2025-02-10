package com.example.e_commerce_mobile.presentation.ui.screens.app_main.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.data.remote.BannerResponse
import com.example.e_commerce_mobile.presentation.navigation.Screens
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun HomeMainScreen(
    navController: NavController = NavController(context = LocalContext.current),
    viewModel: ProductViewModel = hiltViewModel()
) {
    viewModel.fetchLikedProducts()
    val likedProducts by viewModel.likedProducts.collectAsState()
    Log.d("likedProducts", likedProducts.toString())

    viewModel.fetchBanners()
    val banners by viewModel.banners.collectAsState()
    Log.d("banners", banners.toString())

    val userId = viewModel.userId.collectAsState().value?.toInt() ?: -1
    val currentLikedProduct = viewModel.currentLikedProduct.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            if (banners.isNotEmpty()) {
                AdCardDisplay(imageResourceWithDescription = banners)
            }

            // Sale Section
            SectionHeader(title = "Sale", subtitle = "Super summer sale")
            val productsSale = List(6) { index -> }

            Spacer(Modifier.height(20.dp))

            // Recommended Section
            SectionHeader(title = "Recommended", subtitle = "Just for you!")
            val productsRecommended: List<@Composable (Int) -> Unit> = likedProducts.map { product ->
                if(currentLikedProduct?.id == product.id) {
                    product.copy(likedBy = currentLikedProduct.likedBy)
                }
                @Composable { width: Int ->
                    var subSubCategoryName by rememberSaveable { mutableStateOf("") }
                    viewModel.getCurrentSubSubCategory(product.subSubCategory) {
                        subSubCategoryName = it
                    }

                    ProductOverviewCardVertical(
                        productName = product.name,
                        subSubCategory = subSubCategoryName.toString(),
                        width = width,
                        isLiked = product.likedBy.contains(userId),
                        isNew = false,
                        discountPercent = product.discount.toInt(),
                        price = product.price.toDouble(),
                        rating = product.userRating.toFloat(),
                        productImage = product.productImageUrl ?: "",
                        onFavorite = { viewModel.onLike(product.id) }
                    ) {
                        navController.navigate(Screens.ProductDetailScreen.route + "/${product.id}")
                    }
                }
            }
            LazyRowWithKItemsPerScreen(
                k = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3,
                spacer = 16,
                items = productsRecommended
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String, onSeeAllClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold))),
                fontSize = 32.sp
            )
            Text(
                text = subtitle,
                color = Color(0xFF9B9B9B),
                fontSize = 15.sp,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
            )
        }
        TextButton(onClick = onSeeAllClick) {
            Text("View All")
        }
    }
}

@Composable
fun LazyRowWithKItemsPerScreen(
    k: Int,
    spacer: Int = 0,
    items: List<@Composable (Int) -> Unit>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val itemWidth = (screenWidth - ((k - 1) * spacer)) / k

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacer.dp)
    ) {
        items(items.size) { index ->
            items[index](itemWidth)
        }
    }
}

@Composable
fun ProductOverviewCardVertical(
    productName: String = "",
    subSubCategory: String = "",
    width: Int,
    isNew: Boolean = false,
    discountPercent: Int = 5,
    onFavorite: () -> Unit = {},
    productImage: String = "",
    rating: Float = 4.9f,
    price: Double = 1399.0,
    isLiked: Boolean = false,
    onProductOverviewClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(5.dp), spotColor = Color(0xFF9B9B9B))
            .width(width.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable(onClick = onProductOverviewClick)
            .background(Color.White)
            .padding(3.dp)
    ) {
        CustomImageWithFavoriteCircle(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            isNew = isNew,
            onFavorite = onFavorite,
            discountPercent = discountPercent,
            productImage = productImage,
            isLiked = isLiked
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            StarRating(rating = rating)
            Text(
                text = subSubCategory,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)), fontSize = 10.sp),
                fontWeight = FontWeight.W300,
                color = Color(0xFF9B9B9B),
                modifier = Modifier.padding(top = 5.dp)
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = productName,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)), fontSize = 15.sp),
                    fontWeight = FontWeight.W800,
                    color = Color(0xFF222222),
                    maxLines = 2,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        if (discountPercent > 0) {
                            withStyle(style = SpanStyle(color = Color(0xFF9B9B9B), textDecoration = TextDecoration.LineThrough)) {
                                append("$price\$")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFDB3022))) {
                                append("    ${(price - (price * discountPercent / 100))}\$")
                            }
                        } else {
                            withStyle(style = SpanStyle(color = Color(0xFF222222))) {
                                append("$price\$")
                            }
                        }
                    },
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)), fontSize = 12.sp),
                    color = Color(0xFF222222),
                    fontWeight = FontWeight.W100,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}


@Composable
fun ProductOverviewCardHorizontal(
    productName: String = "",
    subSubCategory: String = "",
    isNew: Boolean = true,
    discountPercent: Int = 5,
    isLiked: Boolean = false,
    onFavorite: () -> Unit = {},
    productImage: String = "",
    rating: Float = 4.9f,
    price: Double = 1399.0,
    onProductOverviewClick: () -> Unit = {}){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(155.dp)) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color(0xFF9B9B9B)
                )
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = onProductOverviewClick)
                .background(Color.White)
                .padding(4.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(productImage),
                    contentDescription = "product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                )


                if (isNew) {
                    Surface(modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp, 24.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
                        .align(Alignment.TopStart),
                        color = Color.Black,
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "New", color = Color(0xFFFFFFFF))
                        }
                    }
                }
                if (discountPercent > 0) {
                    Surface(modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp, 24.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
                        .align(Alignment.TopEnd),
                        color = Color(0xFFDB3022),
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "-$discountPercent%", color = Color(0xFFFFFFFF))
                        }
                    }

                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = productName,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                            fontSize = 15.sp
                        ),
                        fontWeight = FontWeight.W800,
                        color = Color(0xFF222222),
                        maxLines = 2,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            if (discountPercent > 0) {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF9B9B9B),
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                ) {
                                    append("$price\$")
                                }

                                withStyle(style = SpanStyle(color = Color(0xFFDB3022))) {
                                    append("    ${(price - (price * discountPercent / 100))}\$")
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Color(0xFF222222))) {
                                    append("$price\$")
                                }
                            }

                        },
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                            fontSize = 12.sp
                        ),
                        color = Color(0xFF222222),
                        fontWeight = FontWeight.W100,
                        maxLines = 2,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                StarRating(rating = rating)
                Text(
                    text = subSubCategory,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 10.sp
                    ),
                    fontWeight = FontWeight.W300,
                    color = Color(0xFF9B9B9B),
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
        Surface(modifier = Modifier
            .size(40.dp)
            .shadow(elevation = 10.dp, shape = CircleShape)
            .align(Alignment.BottomEnd)
            .clickable(onClick = onFavorite),
            color = Color.White,
        ) {
            Icon(painter = painterResource(id = if (isLiked) R.drawable.filled_favorite else R.drawable.outlined_favorite), contentDescription = "favorite", tint = Color.Unspecified, modifier = Modifier.padding(10.dp))

        }
    }
}



@Composable
fun CustomImageWithFavoriteCircle(
    modifier: Modifier = Modifier,
    onFavorite: () -> Unit = {},
    isNew: Boolean = false,
    isLiked: Boolean,
    discountPercent: Int = 20,
    productImage: String
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(productImage),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.93f)
                .clip(RoundedCornerShape(5.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))
        )
        Surface(
            modifier = Modifier
                .size(40.dp)
                .shadow(elevation = 10.dp, shape = CircleShape)
                .align(Alignment.BottomEnd)
                .clickable(onClick = onFavorite),
            color = Color.White,
        ) {
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.filled_favorite else R.drawable.outlined_favorite),
                contentDescription = "favorite",
                tint = Color.Unspecified,
                modifier = Modifier.padding(10.dp)
            )
        }

        if (isNew) {
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .size(40.dp, 24.dp)
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
                    .align(Alignment.TopStart),
                color = Color.Black,
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "New", color = Color(0xFFFFFFFF))
                }
            }
        }
        if (discountPercent > 0) {
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .size(40.dp, 24.dp)
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
                    .align(Alignment.TopEnd),
                color = Color(0xFFDB3022),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "-$discountPercent%", color = Color(0xFFFFFFFF))
                }
            }
        }
    }
}

@Composable
fun AdCard(modifier: Modifier = Modifier, imageResource: String, description: String = "") {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(imageResource),
                contentScale = ContentScale.FillBounds,
                contentDescription = "ad image"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.9f)
                            ),
                            startY = 200f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Text(
                text = description,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold)), fontSize = 26.sp, color = Color(0xFFFFFFFF)),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun AdCardDisplay(imageResourceWithDescription: List<BannerResponse>) {
    val pagerState = rememberPagerState { imageResourceWithDescription.size }
    val pagerIsDragged = pagerState.interactionSource.collectIsDraggedAsState()

    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed = pageInteractionSource.collectIsPressedAsState()

    val autoAdvance = !pagerIsDragged.value && !pageIsPressed.value

    if (autoAdvance) {
        LaunchedEffect(pagerState, pageInteractionSource) {
            while (true) {
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % if (imageResourceWithDescription.isEmpty()) 1 else imageResourceWithDescription.size
                pagerState.animateScrollToPage(nextPage, animationSpec = tween(durationMillis = 2000))
            }
        }
    }
    HorizontalPager(
        state = pagerState,
        pageSpacing = 10.dp
    ) { page ->
        AdCard(imageResource = imageResourceWithDescription[page].adImage, description = imageResourceWithDescription[page].adTitle)
    }
}

@Composable
fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        var numberOfFilledStars = rating.toInt()
        if (abs(rating - numberOfFilledStars) > 0.8) {
            numberOfFilledStars++
        }
        repeat(numberOfFilledStars) {
            Icon(
                painter = painterResource(R.drawable.fully_rated_star),
                contentDescription = "Filled rating Star $it",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        val numberOfHalfFilledStars = if (abs(rating - numberOfFilledStars) !in 0.3..0.8) 0 else 1
        repeat(numberOfHalfFilledStars) {
            Icon(
                painter = painterResource(R.drawable.semi_rated_star),
                contentDescription = "Semi-filled rating Star",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        repeat(starCount - numberOfFilledStars - numberOfHalfFilledStars) {
            Icon(
                painter = painterResource(R.drawable.outlined_star),
                contentDescription = "Empty rating Star",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(5.dp))
        Text(text = "($rating)", color = Color(0xFF9B9B9B), fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
    }
}





@Composable
fun VerticalLazyGridWithKItemsPerScreen(
    modifier: Modifier = Modifier,
    nestedScrollConnection: NestedScrollConnection? = null,
    k: Int,
    spacer: Int = 0,
    items: List<@Composable (Int) -> Unit>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val itemWidth = (screenWidth - ((k - 1) * spacer)) / k
    // the same the
    LazyVerticalGrid(
        modifier = modifier.nestedScroll(nestedScrollConnection ?: return),
        columns = GridCells.Fixed(k),
        verticalArrangement = Arrangement.spacedBy(spacer.dp),
        horizontalArrangement = Arrangement.spacedBy(spacer.dp)
    ) {
        items(items.size) { index ->
            items[index](itemWidth)
        }
    }
}


@Preview(apiLevel = 35)
@Composable
private fun Preview() {
    //ProductOverviewCardHorizontal()
}