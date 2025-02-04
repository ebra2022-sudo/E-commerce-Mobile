package com.example.e_commerce_mobile.data.remote



import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // auth endpoints

    @POST("user/register/")
    suspend fun registerUser(@Body userRegisterForm: UserRegisterForm): Response<UserRegisterResponseWithMessage>

    @POST("user/login/")
    suspend fun loginUser(@Body userLoginForm: UserLoginForm): Response<UserLoginResponseWithMessage>

    @POST("user/logout/")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Unit>

    @GET("product-management/liked-products/")
    suspend fun getLikedProducts(@Header("Authorization") token: String): Response<List<ProductResponse>>


    // product management endpoints
    @GET("product-management/product-hierarchy/")
    suspend fun getProductHierarchy(): Response<List<MainCategoryResponse>>

    @GET("product-management/{subCategoryId}/products-per-sub-category/")
    suspend fun getProducts(@Path("subCategoryId")subCategoryId: Int): Response<List<ProductResponse>>


    @POST("product-management/{productId}/like/")
    suspend fun likeProduct(
        @Header("Authorization") authHeader: String,
        @Path("productId") productId: Int
    ): Response<ProductResponse>

    @POST("order-management/{productId}/add-to-cart/")
    suspend fun addProductToCart(
        @Header("Authorization") authHeader: String,
        @Path("productId") productId: Int,
    ): Response<OrderItem>
    @GET("order-management/orders/")
    suspend fun getOrders(@Header("Authorization") authHeader: String): Response<List<Order>>

    @GET("order-management/order-items/")
    suspend fun getOrderItems(@Header("Authorization") authHeader: String): Response<List<OrderItem>>

    @POST("order-management/{orderItemId}/increase-quantity/")
    suspend fun increaseQuantity(@Header("Authorization") authHeader: String, @Path("orderItemId") orderItemId: Int): Response<OrderItem>

    @POST("order-management/{orderItemId}/decrease-quantity/")
    suspend fun decreaseQuantity(@Header("Authorization") authHeader: String, @Path("orderItemId") orderItemId: Int): Response<OrderItem>

    @GET("order-management/{orderId}/order-details/")
    suspend fun getOrderDetails(@Header("Authorization") authHeader: String, @Path("orderId") orderId: Int): Response<Order>

    @GET("product-management/{productId}/product-details/")
    suspend fun getProductDetails(@Path("productId") productId: Int): Response<ProductResponse>

    @GET("product-management/banners/")
    suspend fun getAdBanners(): Response<List<BannerResponse>>
}




