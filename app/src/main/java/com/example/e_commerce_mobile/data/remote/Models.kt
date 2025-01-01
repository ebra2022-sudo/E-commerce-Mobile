package com.example.e_commerce_mobile.data.remote
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



//
//@Entity(tableName = "products")
//data class Product(
//    @PrimaryKey val id: Int,
//    val name: String,
//    val price: Double,
//    val discount: Double,
//    val mainCategoryId: Int, // Foreign key reference to MainCategory
//    val subCategoryId: Int, // Foreign key reference to SubCategory
//    val subSubCategoryId: Int, // Foreign key reference to SubSubCategory
//    val userRating: Double,
//    val glbFileUrl: String,
//    val productImageUrl: String,
//    val postedAt: String,
//    val updatedAt: String,
//
//)



data class ProductResponse(
    val id: Int,
    val images: List<ProductImage>,
    val name: String,
    val price: String,
    val discount: Double,
    val specifications: List<ProductDescription>,
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



data class ProductDescription(
    val id: Int,
    val key: String,
    val description: String,
    val product: Int
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




@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://192.168.31.240:8000/"

    @Singleton
    @Provides
    fun provideProductApiService(): ProductApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductApiService::class.java)
    }
}







