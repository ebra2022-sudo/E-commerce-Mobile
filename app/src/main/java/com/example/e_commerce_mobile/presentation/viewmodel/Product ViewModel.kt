package com.example.e_commerce_mobile.presentation.viewmodel

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_mobile.data.local.MainCategory
import com.example.e_commerce_mobile.data.local.MainCategoryDao
import com.example.e_commerce_mobile.data.local.SubCategory
import com.example.e_commerce_mobile.data.local.SubCategoryDao
import com.example.e_commerce_mobile.data.local.SubSubCategory
import com.example.e_commerce_mobile.data.local.SubSubCategoryDao
import com.example.e_commerce_mobile.data.remote.BannerResponse
import com.example.e_commerce_mobile.data.remote.ProductResponse
import com.example.e_commerce_mobile.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.fetchAndCacheProductHierarchy()
            productRepository.fetchMainCategories().collect {
                _mainCategories.value = it
            }
        }
    }



    private val _mainCategories = MutableStateFlow<List<MainCategory>>(emptyList())
    val mainCategories: StateFlow<List<MainCategory>> = _mainCategories
    private val _currentSubCategory = MutableStateFlow<SubCategory?>(null)
    val currentSubCategory: StateFlow<SubCategory?> = _currentSubCategory

    private val _currentProducts = MutableStateFlow<List<ProductResponse>>(emptyList())
    val currentProducts: StateFlow<List<ProductResponse>> = _currentProducts

    private val _currentProduct = MutableStateFlow<ProductResponse?>(null)
    val currentProduct: StateFlow<ProductResponse?> = _currentProduct

    private val _subCategories = MutableStateFlow<List<SubCategory>>(emptyList())
    val subCategories: StateFlow<List<SubCategory>> = _subCategories

    private val _banners = MutableStateFlow<List<BannerResponse>>(emptyList())
    val banners: StateFlow<List<BannerResponse>> = _banners

    private val _subSubCategories = MutableStateFlow<List<SubSubCategory>>(emptyList())
    val subSubCategories: StateFlow<List<SubSubCategory>> = _subSubCategories

    private val _currentSubSubCategory = MutableStateFlow<SubSubCategory?>(null)
    val currentSubSubCategory: StateFlow<SubSubCategory?> = _currentSubSubCategory

    private val _modelBuffer = MutableStateFlow<ByteBuffer?>(null)
    val modelBuffer: StateFlow<ByteBuffer?> = _modelBuffer


    fun fetchAndLoadModel(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        val bytes = responseBody.bytes()
                        val buffer = ByteBuffer.allocateDirect(bytes.size)
                            .apply { order(ByteOrder.nativeOrder())
                                put (bytes)
                                rewind ()
                            }
                        withContext (Dispatchers.Main) {
                            _modelBuffer.value = buffer
                        }
                    }
                } else {
                    Log.d("Fetch model", "Error: ${response.message}")
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { Log.d("Fetch model", e.message.toString()) }
            }
        }
    }

    fun fetchBanners() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _banners.value = productRepository.fetchBanners()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Fetch banners", e.message.toString())
                }
            }
        }
    }

    fun fetchSubCategories(mainCategoryId: Int) {
        viewModelScope.launch {
            productRepository.fetchSubCategories(mainCategoryId)
                .collect { list ->
                    _subCategories.value = list
                }
        }
    }


    fun fetchProducts(subCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentProducts.value = productRepository.getProducts(subCategoryId)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Fetch products", e.message.toString())
                }
            }
        }
    }

    fun fetchSubSubCategories(subCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.fetchSubSubCategories(subCategoryId).collect { list ->
                withContext(Dispatchers.Main) {
                    _subSubCategories.value = list
                }
            }
        }
    }

    fun getCurrentSubCategory(subCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentSubCategory.value = productRepository.getCurrentSubCategory(subCategoryId)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Get current sub category", e.message.toString())
                }
            }
        }
    }

    fun getCurrentSubSubCategory(subSubCategoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentSubSubCategory.value = productRepository.getCurrentSubSubCategory(subSubCategoryId)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Get current sub sub category", e.message.toString())
                }
            }
        }
    }



    fun onLike(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productRepository.onLikeProduct(productId)
            } catch (e: NetworkErrorException) {
                withContext(Dispatchers.Main) {
                    Log.d("OnLike", e.message.toString())
                }
            }
        }
    }

    fun getCurrentProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentProduct.value = productRepository.getProductDetails(productId)
                withContext(Dispatchers.Main) {
                    Log.d("currentProduct", _currentProduct.value.toString())
                }
            } catch (e: NetworkErrorException) {
                withContext(Dispatchers.Main) {
                    Log.d("Get current product", e.message.toString())
                }
            }
        }
    }
}
