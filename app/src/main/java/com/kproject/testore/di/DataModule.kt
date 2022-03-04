package com.kproject.testore.di

import android.content.Context
import androidx.room.Room
import com.kproject.testore.data.database.CartDAO
import com.kproject.testore.data.database.CartDatabase
import com.kproject.testore.data.repositories.CartDatabaseRepository
import com.kproject.testore.data.repositories.CartDatabaseRepositoryImpl
import com.kproject.testore.data.repositories.ProductRepository
import com.kproject.testore.data.repositories.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideProductRepository(): ProductRepository {
        return ProductRepositoryImpl()
    }

    @Singleton
    @Provides
    fun cartDatabaseRepository(cartDatabase: CartDatabase): CartDatabaseRepository {
        return CartDatabaseRepositoryImpl(cartDatabase.cartDAO())
    }

    @Singleton
    @Provides
    fun provideCartDatabase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(
            context,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }
}