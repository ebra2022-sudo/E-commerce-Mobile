package com.example.e_commerce_mobile.data.remote
import com.google.gson.annotations.SerializedName


// auth - models

data class UserLoginForm(
    val email: String,
    val password: String
)

data class UserLoginResponseWithMessage(
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("access_token") val accessToken: String,
)

data class UserRegisterForm(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val password: String
)

data class UserRegisterResponse(
    val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
)

data class UserRegisterResponseWithMessage(
    @SerializedName("status_message") val statusMessage: String,
    val user: UserRegisterResponse
)



// product-Repository

data class ProductResponse(
    val id: Int,
    val images: List<ProductImage>,
    val name: String,
    val price: String,
    val discount: Double,
    val specifications: List<ProductSpec>,
    @SerializedName("is_liked") val isLiked: Boolean,
    @SerializedName("user_rating") val userRating: Double,
    @SerializedName("glb_file")val glbFile: String?,
    @SerializedName("product_image_url")val productImageUrl: String?,
    @SerializedName("created_at")val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("main_category") val mainCategory: Int,
    @SerializedName("sub_category") val subCategory: Int,
    @SerializedName("sub_sub_category") val subSubCategory: Int
)

data class ProductImage(
    val id: Int,
    val product: Int,
    val image: String?
)



data class ProductSpec(
    val id: Int,
    val key: String,
    val description: String,
    val product: Int
)

data class BannerResponse(
    val id: Int,
    @SerializedName("ad_title") val adTitle: String,
    @SerializedName("ad_image") val adImage: String
)




data class MainCategoryResponse(
    val id: Int,
    val name: String,
    @SerializedName("subcategories") val subCategories: List<SubCategoryResponse>
)

//  d
data class SubCategoryResponse(
    val id: Int,
    val name: String,
    @SerializedName("image_resource") val imageResource: String,
    @SerializedName("subsubcategories") val subSubCategories: List<SubSubCategoryResponse>
)

data class SubSubCategoryResponse(
    val id: Int,
    val name: String
)










