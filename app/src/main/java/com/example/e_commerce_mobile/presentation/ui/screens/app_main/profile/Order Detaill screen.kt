package com.example.e_commerce_mobile.presentation.ui.screens.app_main.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.data.remote.OrderItem
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen1(modifier: Modifier = Modifier,
                       navController: NavController = NavController(context = LocalContext.current)) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Order Details",
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.metropolis_medium))),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(R.drawable.back_arrow), contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

        }
    }
}

@Composable
fun OrderDetailsScreen(
    viewModel: ProductViewModel= hiltViewModel(),
    navController: NavController = NavController(context = LocalContext.current),
    orderId: Int) {
    Log.d("Order Id: ", orderId.toString())

    viewModel.fetchOrderDetails(orderId)

    val orderDetails = viewModel.orderDetails.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Order No: ${orderDetails?.orderNumber}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Tracking number: ${orderDetails?.trackingNumber}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Delivered",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Items", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        // design the list of the items


        OrderItem(
            orderStatus = orderDetails?.status.toString(),
            orderNumber = orderDetails?.orderNumber.toString(),
            trackingNumber = orderDetails?.trackingNumber.toString(),
            quantity = orderDetails?.item?.quantity ?: 0,
            pricePerUnit = orderDetails?.item?.price?.toDouble() ?: 0.0,
            orderDate = orderDetails?.orderDate.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        // sample od the  sate of
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Order Information", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Shipping Address: ${orderDetails?.shippingAddress}")
        Text(text = "Payment method: ${orderDetails?.paymentMethod}")
        Text(text = "Delivery method: ${orderDetails?.deliveryMethod}")
        Text(text = "Discount: ${orderDetails?.discount}")
        Text(text = "Total Amount: ${orderDetails?.totalAmount}")

        Spacer(modifier = Modifier.height(16.dp))
        // deisng tehsate of teh  crreunt valie ofhe

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Reorder action */ }) {
                Text(text = "Reorder")
            }
            Button(onClick = { /* Leave feedback action */ }) {
                Text(text = "Leave Feedback")
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
private fun Preview() {

    // design the system
}