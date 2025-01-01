package com.example.e_commerce_mobile.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "e_commerce_database"
        ).build()
    }

    @Provides
    fun provideMainCategoryDao(database: AppDatabase): MainCategoryDao {
        return database.mainCategoryDao()
    }

    @Provides
    fun provideSubCategoryDao(database: AppDatabase): SubCategoryDao {
        return database.subCategoryDao()
    }

    @Provides
    fun provideSubSubCategoryDao(database: AppDatabase): SubSubCategoryDao {
        return database.subSubCategoryDao()
    }
}