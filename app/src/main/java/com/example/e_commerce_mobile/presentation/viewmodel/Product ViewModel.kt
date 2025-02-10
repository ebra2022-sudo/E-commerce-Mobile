package com.example.e_commerce_mobile.presentation.viewmodel

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_mobile.data.local.MainCategory
import com.example.e_commerce_mobile.data.local.SubCategory
import com.example.e_commerce_mobile.data.local.SubSubCategory
import com.example.e_commerce_mobile.data.remote.BannerResponse
import com.example.e_commerce_mobile.data.remote.Order
import com.example.e_commerce_mobile.data.remote.OrderItem
import com.example.e_commerce_mobile.data.remote.ProductResponse
import com.example.e_commerce_mobile.data.repositories.ProductRepository
import com.example.e_commerce_mobile.utils.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val userSession: UserSession
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.fetchAndCacheProductHierarchy()
            productRepository.fetchMainCategories().collect {
                _mainCategories.value = it
            }
        }
    }
    val accessToken: StateFlow<String?> = userSession.accessToken
    val isLoggedIn: StateFlow<Boolean> = userSession.isLoggedIn
    val userId: StateFlow<String?> = userSession.userId

    init {
        Log.d("ViewModel | accessToken", accessToken.value.toString())
        Log.d("ViewModel | isLoggedIn", isLoggedIn.value.toString())
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



    private val _modelBuffer = MutableStateFlow<ByteBuffer?>(null)
    val modelBuffer: StateFlow<ByteBuffer?> = _modelBuffer


    private val _likedProducts = MutableStateFlow<List<ProductResponse>>(emptyList())
    val likedProducts: StateFlow<List<ProductResponse>> = _likedProducts

    private val _currentLikedProduct = MutableStateFlow<ProductResponse?>(null)
    val currentLikedProduct: StateFlow<ProductResponse?> = _currentLikedProduct



    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> get() = _orderItems

    private val _orderDetails = MutableStateFlow<Order?>(null)
    val orderDetails: StateFlow<Order?> get() = _orderDetails

    private val _addProductResult = MutableStateFlow<OrderItem?>(null)
    val addProductResult: StateFlow<OrderItem?> get() = _addProductResult



    val _currentProductsBySubSubCategoryId  = MutableStateFlow(emptyList<ProductResponse>())
    val currentProductsBySubSubCategoryId: StateFlow<List<ProductResponse>> = _currentProductsBySubSubCategoryId



    fun addProductToCart(productId: Int) {
        viewModelScope.launch {
            try {
                val response = productRepository.addProductToCart(productId, accessToken.value.toString())
                _addProductResult.value = response.body()
            } catch (e: Exception) {
                // Handle errors (e.g., log them or update UI)
            }
        }
    }

    fun increaseQuantity(orderItemId: Int) {
        viewModelScope.launch {
            try {
                val response = productRepository.increaseQuantity(orderItemId, accessToken.value.toString())
                _addProductResult.value = response.body()
            } catch (e: Exception) {

            }
        }
    }


    fun decreaseQuantity(orderItemId: Int) {
        viewModelScope.launch {
            try {
                val response =
                    productRepository.decreaseQuantity(orderItemId, accessToken.value.toString())
                _addProductResult.value = response.body()
            } catch (e: Exception) {

            }
        }
    }

    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val response = productRepository.getOrders(accessToken.value.toString())
                if (response.isSuccessful) {
                    _orders.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                // Handle errors (e.g., log them or update UI)
            }
        }
    }

    fun fetchOrderItems() {
        viewModelScope.launch {
            try {
                productRepository.getOrderItems(accessToken.value.toString()).let {
                    _orderItems.value = it
                }

            } catch (e: Exception) {
                // Handle errors (e.g., log them or update UI)
                Log.d("fetchOrderItems", e.message.toString())
            }
        }
    }

    fun fetchOrderDetails(orderId: Int) {
        viewModelScope.launch {
            try {
                val response = productRepository.getOrderDetails(orderId, accessToken.value.toString())
                if (response.isSuccessful) {
                    _orderDetails.value = response.body()
                }
            } catch (e: Exception) {
                // Handle errors (e.g., log them or update UI)
            }
        }
    }

    // sample

    fun fetchLikedProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("fetchLikedProducts", accessToken.value.toString())
            productRepository.getLikedProducts(accessToken.value.toString()).let {
                _likedProducts.value = it
            }
        }
    }
    //sample case  state of sample


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
                _currentProducts.value = productRepository.getProductsBySubCategory(subCategoryId).body()?: emptyList()
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

    fun getCurrentSubSubCategory(subSubCategoryId: Int, callBack: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val subSubCategoryName = productRepository.getCurrentSubSubCategory(subSubCategoryId)?.name

                withContext(Dispatchers.Main) {
                    callBack(subSubCategoryName ?: "")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Get current sub sub category", e.message.toString())
                }

            }
        }

    }

    fun onLike(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // Ensure the access token is not null or empty
            if (accessToken.value.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    Log.d("OnLike", "Access token is missing")
                    return@withContext
                }
            }

            try {
                // Make the API call to like the product
                val response = productRepository.onLikeProduct(productId, accessToken.value.toString())

                // Check if the response is successful
                
                if (response.isSuccessful) {
                    _currentLikedProduct.value = response.body()
                    withContext(Dispatchers.Main) {
                        //sample case design the sample e sample sample case state of the  given value of the product
                        Log.d("OnLike", "Product liked successfully")
                        // Handle success (e.g., update the UI or show a toast)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.d("OnLike", "Failed to like product. Response code: ${response.code()}")
                        // Handle failure (e.g., show a toast or error message)
                    }
                }
            } catch (e: NetworkErrorException) {
                // Handle network-related exceptions
                withContext(Dispatchers.Main) {
                    Log.d("OnLike", "Network error: ${e.message}")
                    // Show an error message or retry option to the user
                }
                //
            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    Log.d("OnLike", "Error: ${e.message}")
                    // Handle generic error
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

    fun getProductsBySubSubCategory(subSubCategoryId: Int) {
        viewModelScope.launch {
            if (subSubCategoryId == -1) {
                try {
                    val response = productRepository.getProductsBySubCategory(_currentSubCategory.value?.id?:6)
                    if (response.isSuccessful) {
                        _currentProductsBySubSubCategoryId.value =response.body() ?: emptyList()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Get products by sub sub category", e.message.toString())
                    }
                }

            }
            else {
                try {
                    val response = productRepository.getProductsBySubSubCategory(subSubCategoryId)
                    if (response.isSuccessful) {
                        _currentProductsBySubSubCategoryId.value =response.body() ?: emptyList()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Get products by sub sub category", e.message.toString())
                    }
                }

            }

        }
    }
}
