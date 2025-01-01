package com.example.e_commerce_mobile.domain.repositories

import android.util.Log
import com.example.e_commerce_mobile.data.local.MainCategory
import com.example.e_commerce_mobile.data.local.MainCategoryDao
import com.example.e_commerce_mobile.data.local.SubCategory
import com.example.e_commerce_mobile.data.local.SubCategoryDao
import com.example.e_commerce_mobile.data.local.SubSubCategory
import com.example.e_commerce_mobile.data.local.SubSubCategoryDao
import com.example.e_commerce_mobile.data.remote.ProductApiService
import com.example.e_commerce_mobile.data.remote.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val apiService: ProductApiService,
    private val mainCategoryDao: MainCategoryDao,
    private val subCategoryDao: SubCategoryDao,
    private val subSubCategoryDao: SubSubCategoryDao
) {

    suspend fun fetchAndCacheProductHierarchy() {
        try {
            val response = apiService.getProductHierarchy()

            // Insert main categories into the database
            response.forEach { mainCategory ->
                val mainCategoryEntity = MainCategory(id = mainCategory.id, name = mainCategory.name)
                mainCategoryDao.insert(mainCategoryEntity)

                // Insert subcategories into the database
                mainCategory.subCategories.forEach { subCategory ->
                    val subCategoryEntity = SubCategory(
                        id = subCategory.id,
                        name = subCategory.name,
                        image = subCategory.imageResource,
                        mainCategoryId = mainCategory.id
                    )
                    subCategoryDao.insert(subCategoryEntity)

                    // Insert sub-subcategories into the database
                    subCategory.subSubCategories.forEach { subSubCategory ->
                        val subSubCategoryEntity = SubSubCategory(
                            id = subSubCategory.id,
                            name = subSubCategory.name,
                            subCategoryId = subCategory.id
                        )
                        subSubCategoryDao.insert(subSubCategoryEntity)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Product Repository", e.message.toString())
            // Handle error
        }
    }


}
