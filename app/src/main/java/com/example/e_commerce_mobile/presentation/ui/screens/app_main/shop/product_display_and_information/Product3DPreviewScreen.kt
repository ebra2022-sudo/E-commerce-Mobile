package com.example.e_commerce_mobile.presentation.ui.screens.app_main.shop.product_display_and_information


import android.content.Context
import android.view.*
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.e_commerce_mobile.R
import com.example.e_commerce_mobile.presentation.viewmodel.ProductViewModel
import com.example.e_commerce_mobile.utils.ModelRenderer
import java.nio.ByteBuffer


@Composable
fun Product3DPreviewScreen(
    url: String,
    viewModel: ProductViewModel =  hiltViewModel(),
    navController: NavController = NavController(LocalContext.current),
    lifecycle: Lifecycle,
    context: Context
) {
    viewModel.fetchAndLoadModel(url)
    val modelBuffer = viewModel.modelBuffer.collectAsState().value
    var arMode by  rememberSaveable {mutableStateOf(false)}
    Box(modifier = Modifier.fillMaxSize()){
        if (modelBuffer == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            ThreeDRendering(byteBuffer = modelBuffer, lifecycle = lifecycle, context = context, arMode = arMode)
            IconButton(modifier = Modifier.align(Alignment.TopStart), onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "back")
            }
            Box(modifier = Modifier
                .padding(8.dp)
                .size(30.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                .align(Alignment.TopEnd)
                .clickable(onClick = { arMode = !arMode })) {
                Text(text = if (arMode) "3D" else "AR", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ThreeDRendering(
    lifecycle: Lifecycle,
    byteBuffer: ByteBuffer,
    context: Context,
    arMode: Boolean
) {
    val renderer = ModelRenderer(byteBuffer, context)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFD8E0D1))) {
        if (arMode) CameraPreview(context = context)
        AndroidView(
            factory = { context ->
                SurfaceView(context).apply {
                    renderer.onSurfaceAvailable(this, lifecycle)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun CameraPreview(
    context: Context
) {
    val controller = remember {
        LifecycleCameraController(context.applicationContext)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                setBackgroundColor(Color.Transparent.toArgb())
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}