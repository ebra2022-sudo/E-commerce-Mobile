package com.example.e_commerce_mobile.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.view.Choreographer
import android.view.SurfaceView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.e_commerce_mobile.R
import com.google.android.filament.Box
import com.google.android.filament.EntityManager
import com.google.android.filament.IndexBuffer
import com.google.android.filament.Material
import com.google.android.filament.RenderableManager
import com.google.android.filament.VertexBuffer
import com.google.android.filament.View
import com.google.android.filament.android.UiHelper
import com.google.android.filament.utils.KTX1Loader
import com.google.android.filament.utils.ModelViewer
import java.nio.ByteBuffer

class ModelRenderer(private val modelByteBuffer: ByteBuffer, private val context: Context) {
    private lateinit var assets: AssetManager
    private lateinit var uiHelper: UiHelper
    private lateinit var lifecycle: Lifecycle
    private lateinit var surfaceView: SurfaceView
    private val choreographer: Choreographer = Choreographer.getInstance() // Initialize here
    private val frameScheduler = FrameCallback()
    private lateinit var modelViewer: ModelViewer






    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            choreographer.postFrameCallback(frameScheduler)
        }

        override fun onPause(owner: LifecycleOwner) {
            choreographer.removeFrameCallback(frameScheduler)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            choreographer.removeFrameCallback(frameScheduler)
            lifecycle.removeObserver(this)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onSurfaceAvailable(surfaceView: SurfaceView, lifecycle: Lifecycle) {
        this.surfaceView = surfaceView
        this.lifecycle = lifecycle
        assets = surfaceView.context.assets
        lifecycle.addObserver(lifecycleObserver)
        uiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK).apply {
            isOpaque = false
        }
        modelViewer = ModelViewer(surfaceView = surfaceView, uiHelper = uiHelper)
        surfaceView.setOnTouchListener { _, event ->
            modelViewer.onTouchEvent(event)
            true
        }
        modelViewer.view.blendMode = View.BlendMode.TRANSLUCENT
        modelViewer.renderer.clearOptions = modelViewer.renderer.clearOptions.apply {
            clear = true
        }
        modelViewer.view.apply {
            renderQuality = renderQuality.apply {
                hdrColorBuffer = View.QualityLevel.MEDIUM
            }
        }
        modelViewer.view.setShadowType(View.ShadowType.VSM)
        createRenderables()

        val skybox = context.resources.openRawResource(R.raw.downloads_skybox).use { inputStream ->
            val buffer = ByteBuffer.wrap(inputStream.readBytes())
            KTX1Loader.createSkybox(modelViewer.engine, buffer)
        }
        // Create a ground plane entity




        val ibl = context.resources.openRawResource(R.raw.downloads_ibl).use { inputStream ->
            val buffer = ByteBuffer.wrap(inputStream.readBytes())
            KTX1Loader.createIndirectLight(modelViewer.engine, buffer)
        }
        modelViewer.scene.indirectLight = ibl




        // Create and store the light entity


    }

    private fun createRenderables() {
        modelViewer.loadModelGlb(modelByteBuffer)
        modelViewer.transformToUnitCube()
    }
    //  sample of the sat of the current
    inner class FrameCallback : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(this)
            modelViewer.render(frameTimeNanos)
        }
    }
}