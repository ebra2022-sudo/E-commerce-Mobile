package com.example.e_commerce_mobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [MainCategory::class, SubCategory::class, SubSubCategory::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainCategoryDao(): MainCategoryDao
    abstract fun subCategoryDao(): SubCategoryDao
    abstract fun subSubCategoryDao(): SubSubCategoryDao
}


///