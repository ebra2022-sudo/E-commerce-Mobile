package com.example.e_commerce_mobile.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("product-management/product-hierarchy")
    suspend fun getProductHierarchy(): List<MainCategoryResponse>

    @GET("product-management/{subCategoryId}/products-per-sub-category")
    suspend fun getProducts(@Path("subCategoryId")subCategoryId: Int): List<ProductResponse>

    @POST("product-management/{productId}/like/")
    suspend fun likeProduct(@Path("productId") productId: Int)

    @GET("product-management/{productId}/product-details/")
    suspend fun getProductDetails(@Path("productId") productId: Int): ProductResponse

}


