package com.example.e_commerce_mobile.presentation.ui.screens.app_main.bag

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BagMainScreen(viewModel: ProductViewModel = hiltViewModel()) {
    viewModel.fetchOrderItems()
    val orderItems = viewModel.orderItems.collectAsState().value
    val addProductResult = viewModel.addProductResult.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            MediumTopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = "My Cart", modifier = Modifier.fillMaxWidth(),
                        textAlign = if (scrollBehavior.state.collapsedFraction >= 0.75f) TextAlign.Center else TextAlign.Start,
                        fontSize = 30.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_bold)))

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
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
        if (orderItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "No items in bag", modifier = Modifier.align(Alignment.Center))
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White).nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                Log.d("orderItems", orderItems.toString())
                items(orderItems.sortedBy { it.productName }) {
                    if (addProductResult?.productId == it.productId) {
                        it.copy(
                            quantity = addProductResult.quantity
                        )
                    }


                    BagItem(
                        productImage = it.imageUrl,
                        productName = it.productName,
                        productPrice = it.price.toString(),
                        quantity = it.quantity,
                        onPlus = { viewModel.increaseQuantity(it.id) },
                        onMinus = { viewModel.decreaseQuantity(it.id) },
                    )
                }
            }
        }
    }
}


@Composable
fun BagItem(
    productImage: String = "",
    productName: String = "",
    productPrice: String = "",
    onPlus: () -> Unit = {},
    onMinus: () -> Unit = {},
    onDelete: () -> Unit = {},
    onMenu: () -> Unit = {},
    onCheckOut: () -> Unit = {},
    quantity: Int = 0

) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(8.dp).clickable(onClick = onCheckOut),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .border(1.dp,
                    color = Color(0x2F9B9B9B),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
                painter = rememberAsyncImagePainter(productImage),
                contentDescription = "product image",
                contentScale = ContentScale.Crop)
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            text = productName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                        )
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF9B9B9B))) {
                                append("Brand: ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF222222))) {
                                append("Apple   ")
                            }

                            withStyle(style = SpanStyle(color = Color(0xFF9B9B9B))) {
                                append("Color: ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF222222))) {
                                append("Black")
                            }
                        }
                        )
                    }
                    Icon(painter = painterResource(id = R.drawable.dots_vertical),
                        contentDescription = "menu", tint = Color(0xFF9B9B9B))
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            modifier = Modifier
                                .size(40.dp).clickable(onClick = onMinus)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape,
                                    spotColor = Color(0xFF9B9B9B)
                                ),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.minus),
                                    contentDescription = "google icon",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                        Text(
                            text = quantity.toString(), fontSize = 24.sp,
                            color = Color(0xFF222222),
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                        )
                        Card(
                            modifier = Modifier
                                .size(40.dp).clickable(onClick = onPlus)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape,
                                    spotColor = Color(0xFF9B9B9B)
                                ),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.plus),
                                    contentDescription = "google icon",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    Text(
                        text = productPrice,
                        fontSize = 18.sp,
                        color = Color(0xFF222222),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium)))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        BagMainScreen()
        Spacer(modifier = Modifier.height(200.dp))
        BagItem()

    }
}