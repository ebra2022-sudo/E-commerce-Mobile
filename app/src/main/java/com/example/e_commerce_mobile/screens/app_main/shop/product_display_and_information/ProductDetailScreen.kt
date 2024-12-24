package com.example.e_commerce_mobile.screens.app_main.shop.product_display_and_information

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier,
                        navController: NavController = NavController(context = LocalContext.current)
) {
    
}

@Composable
fun ProductViewPresenter(modifier: Modifier = Modifier) {
    
}



@Composable
fun HorizontalPagerDemo(imageResources: List<Int> = emptyList()) {
    val pagerState = rememberPagerState(pageCount = {imageResources.size})
    val coroutineScope = rememberCoroutineScope()
    val pagerIsDragged  = pagerState.interactionSource.collectIsDraggedAsState()

    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed  = pageInteractionSource.collectIsPressedAsState()

    // Stop auto-advancing when pager is dragged or one of the pages is pressed
    val autoAdvance = !pagerIsDragged.value&& !pageIsPressed.value
    var autoRotate by remember { mutableStateOf(true) }

    if (autoAdvance && autoRotate) {
        LaunchedEffect(pagerState, pageInteractionSource) {
            while (true) {
                delay(4000)
                val nextPage = (pagerState.currentPage + 1) % imageResources.size
                pagerState.animateScrollToPage(nextPage, animationSpec = tween(durationMillis = 1000))
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
        HorizontalPager( // Number of pages
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 10.dp,
            //pageSize = PageSize.Fixed(250.dp),
            contentPadding = PaddingValues(horizontal = 60.dp)
        ) { page ->
            val pageOffset = pagerState.currentPageOffsetFraction

            // Gradually scale size based on the offset
            val scale = if(page == pagerState.currentPage) 1f else 1f - (0.3f * ( 0.5f - abs(pageOffset)))



            // Page Content
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                modifier = Modifier.fillMaxHeight(scale)
            ){
                Image(
                    painter = painterResource(imageResources[page]),
                    contentDescription = "Preview ${page + 1}",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        // Pager Indicators
        Row(
            Modifier.align(alignment = Alignment.BottomCenter)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                coroutineScope.launch {
                    // Scroll to the next page
                    val nextPage = (pagerState.currentPage - 1).coerceAtMost(imageResources.size - 1)
                    pagerState.animateScrollToPage(nextPage, animationSpec = tween(durationMillis = 1000))

                }

            },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )) {
                Icon(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "arrow left")
            }

            IconButton(onClick = {
                coroutineScope.launch {
                    // Scroll to the next page
                    val nextPage = (pagerState.currentPage + 1).coerceAtMost(imageResources.size - 1)
                    pagerState.animateScrollToPage(nextPage, animationSpec = tween(durationMillis = 1000))
                }
            },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )) {
                Icon(painter = painterResource(id = R.drawable.forward_arrow), contentDescription = "arrow left")
            }
        }
        Icon(painter = painterResource(R.drawable.auto_rotate), contentDescription = "icon", tint = if (autoRotate) Color.DarkGray else Color.LightGray ,
            modifier = Modifier.align(Alignment.TopEnd).padding(10.dp).clickable(onClick = {autoRotate = !autoRotate}))

    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    val imageResources = listOf(
        R.drawable.watch_hermes_ultra_digitalmat_gallery_1_202409,
        R.drawable.watch_hermes_ultra_digitalmat_gallery_2_202409,
        R.drawable.watch_hermes_ultra_digitalmat_gallery_3_202409,
        R.drawable.watch_hermes_ultra_digitalmat_gallery_4_202409,
        R.drawable.watch_hermes_ultra_digitalmat_gallery_5_202409)
    HorizontalPagerDemo(imageResources = imageResources)
}