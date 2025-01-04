package com.example.e_commerce_mobile.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "main_categories")
data class MainCategory(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(
    tableName = "sub_categories",
    foreignKeys = [
        ForeignKey(
            entity = MainCategory::class,
            parentColumns = ["id"],
            childColumns = ["mainCategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["mainCategoryId"])]
)
data class SubCategory(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val mainCategoryId: Int
)


@Entity(
    tableName = "sub_sub_categories",
    foreignKeys = [
        ForeignKey(
            entity = SubCategory::class,
            parentColumns = ["id"],
            childColumns = ["subCategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["subCategoryId"])]
)
data class SubSubCategory(
    @PrimaryKey val id: Int,
    val name: String,
    val subCategoryId: Int
)