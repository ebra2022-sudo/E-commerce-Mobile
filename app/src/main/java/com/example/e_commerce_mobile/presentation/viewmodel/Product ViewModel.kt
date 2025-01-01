package com.example.e_commerce_mobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_mobile.data.local.MainCategory
import com.example.e_commerce_mobile.data.local.MainCategoryDao
import com.example.e_commerce_mobile.data.local.SubCategory
import com.example.e_commerce_mobile.data.local.SubCategoryDao
import com.example.e_commerce_mobile.data.local.SubSubCategory
import com.example.e_commerce_mobile.data.local.SubSubCategoryDao
import com.example.e_commerce_mobile.data.remote.ProductResponse
import com.example.e_commerce_mobile.domain.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val mainCategoryDao: MainCategoryDao,
    private val subCategoryDao: SubCategoryDao,
    private val subSubCategoryDao: SubSubCategoryDao
) : ViewModel() {
    init {
        viewModelScope.launch {
            // Fetch and cache product hierarchy
            fetchAndCacheProductHierarchy()
            // Collect data from the Flow
            mainCategoryDao.getAll().collect { categories ->
                _mainCategories.value = categories
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


    // ViewModel implementation

    private val _subCategories = MutableStateFlow<List<SubCategory>>(emptyList())
    val subCategories: StateFlow<List<SubCategory>> = _subCategories



    private val _subSubCategories = MutableStateFlow<List<SubSubCategory>>(emptyList())
    val subSubCategories: StateFlow<List<SubSubCategory>> = _subSubCategories

    private val _currentSubSubCategory = MutableStateFlow<SubSubCategory?>(null)
    val currentSubSubCategory: StateFlow<SubSubCategory?> = _currentSubSubCategory

    // Function to fetch and observe subcategories
    fun fetchSubCategories(mainCategoryId: Int) {
        viewModelScope.launch {
            subCategoryDao.getByMainCategoryId(mainCategoryId)
                .collect { list ->
                    _subCategories.value = list
                }
        }
    }


    fun fetchProducts(subCategoryId: Int) {
        viewModelScope.launch {
            _currentProducts.value = productRepository.apiService.getProducts(subCategoryId)

        }
    }


    // Function to fetch and observe sub-subcategories
    fun fetchSubSubCategories(subCategoryId: Int) {
        viewModelScope.launch {
            subSubCategoryDao.getBySubCategoryId(subCategoryId)
                .collect { list ->
                    _subSubCategories.value = list
                }
        }
    }

    fun getCurrentSubCategory(subCategoryId: Int) {
        viewModelScope.launch {
            _currentSubCategory.value = subCategoryDao.getById(subCategoryId)
        }
    }

    fun getCurrentSubSubCategory(subSubCategoryId: Int) {
        viewModelScope.launch {
            _currentSubSubCategory.value = subSubCategoryDao.getById(subSubCategoryId)
        }
    }

    // Function to fetch and cache product hierarchy
    fun fetchAndCacheProductHierarchy() {
        viewModelScope.launch {
            productRepository.fetchAndCacheProductHierarchy()
        }
    }

    fun onLike(productId: Int) {

        viewModelScope.launch {
            productRepository.apiService.likeProduct(productId)
        }
    }

    fun getCurrentProduct(productId: Int) {
        viewModelScope.launch {
            _currentProduct.value = productRepository.apiService.getProductDetails(productId)
            Log.d("currentProduct", _currentProduct.value.toString())
        }
    }
}
