package com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.navigation.Screens
import com.example.e_commerce_mobile.presentation.ui.screens.auth.HeadlineAppBar
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun MyOrdersScreen(modifier: Modifier = Modifier,
                   navController: NavController = NavController(LocalContext.current),
                   viewModel: ProductViewModel = hiltViewModel()
) {
    viewModel.fetchOrders()
    val orders = viewModel.orders.collectAsState().value
    Scaffold(
        topBar = { HeadlineAppBar(title = "My Orders", onNavigationClick = {navController.navigateUp()}) }
    ) {
        val pagerState = rememberPagerState { 4}
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    // Custom color for the tab indicator
                },
            ) {

                DeliveryStatus.entries.forEachIndexed { index, deliveryStatus ->
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
                                        if (pagerState.currentPage == index) Color(0xFF222222) else Color.Transparent
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    ),
                                    text = deliveryStatus.name,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.W300,
                                    fontSize = 20.sp,
                                    color = if (pagerState.currentPage == index) Color.White else Color(
                                        0xFF222222
                                    ),
                                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                                )
                            }
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            {
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(orders.sortedBy { it.orderDate}) {
                        OrderItem(
                            orderStatus = it.status,
                            orderNumber = it.orderNumber,
                            trackingNumber = it.trackingNumber,
                            quantity = it.item.quantity,
                            pricePerUnit = it.item.price.toDouble(),
                            orderDate = it.orderDate
                            ) {
                            Log.d("Order Number: ", it.id.toString())

                            navController.navigate(Screens.OrderDetailsScreen.route + "/${it.id}")
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun OrderItem(
    orderStatus: String,
    orderNumber: String = "123456789",
    trackingNumber: String = "123456789",
    quantity: Int = 3,
    pricePerUnit: Double = 112.0,
    orderDate: String = "01-04-2025",
    onDetailsClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222),
                        )) {
                        append("Order N")
                    }
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222),
                        textDecoration = TextDecoration.Underline)) {
                        append("o")
                    }
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222))) {
                        append(" $orderNumber")
                    }
                }, fontWeight = FontWeight.Bold)
                Text(text = orderDate,fontSize = 15.sp,
                    color = Color(0xFF9B9B9B))
                
            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontSize = 15.sp,
                    color = Color(0xFF9B9B9B),
                )) {
                    append("Tracking Number: ")

                }
                withStyle(style = SpanStyle(
                    fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF222222))) {
                    append(trackingNumber)
                }
            })

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 15.sp,
                        color = Color(0xFF9B9B9B),
                    )) {
                        append("Quantity: ")

                    }
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF222222))) {
                        append(quantity.toString())
                    }
                })
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 15.sp,
                        color = Color(0xFF9B9B9B),
                    )) {
                        append("Total Amount: ")

                    }
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.metropolis_medium)),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF222222))) {
                        append((pricePerUnit * quantity.toDouble()).toString() + "$")
                    }
                })
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = onDetailsClick,
                ) {
                    Text(text = "Details", color = Color(0xFF222222))
                }
                Text(text = orderStatus,
                    color = Color(0xFF2AA952),
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))))
            }
        }
    }
}

enum class DeliveryStatus {
    Delivered,
    Processing,
    Cancelled,
    Cart
}

@Preview
@Composable
private fun Preview() {
    MyOrdersScreen()
    //OrderItem()
    
}