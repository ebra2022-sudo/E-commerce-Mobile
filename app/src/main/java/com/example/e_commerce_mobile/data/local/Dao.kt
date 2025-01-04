package com.example.e_commerce_mobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MainCategoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mainCategory: MainCategory)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mainCategories: List<MainCategory>)


    @Update
    suspend fun update(mainCategory: MainCategory)


    @Query("SELECT * FROM main_categories WHERE id = :id")
    suspend fun getById(id: Int): MainCategory?


    @Query("SELECT * FROM main_categories")
    fun getAll(): Flow<List<MainCategory>>


    @Query("DELETE FROM main_categories")
    suspend fun deleteAll()
}



@Dao
interface SubCategoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subCategory: SubCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subCategories: List<SubCategory>)


    @Update
    suspend fun update(subCategory: SubCategory)


    @Query("SELECT * FROM sub_categories WHERE id = :id")
    suspend fun getById(id: Int): SubCategory?


    @Query("SELECT * FROM sub_categories WHERE mainCategoryId = :mainCategoryId")
    fun getByMainCategoryId(mainCategoryId: Int): Flow<List<SubCategory>>

    @Query("DELETE FROM sub_categories")
    suspend fun deleteAll()
}


@Dao
interface SubSubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subSubCategory: SubSubCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subSubCategories: List<SubSubCategory>)


    @Update
    suspend fun update(subSubCategory: SubSubCategory)

    @Query("SELECT * FROM sub_sub_categories WHERE id = :id")
    suspend fun getById(id: Int): SubSubCategory?

    @Query("SELECT * FROM sub_sub_categories WHERE subCategoryId = :subCategoryId")
    fun getBySubCategoryId(subCategoryId: Int): Flow<List<SubSubCategory>>

    @Query("DELETE FROM sub_sub_categories")
    suspend fun deleteAll()
}



