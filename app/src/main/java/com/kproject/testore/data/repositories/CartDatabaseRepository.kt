package com.kproject.testore.data.repositories

import androidx.lifecycle.LiveData
import com.kproject.testore.data.CartProduct

interface CartDatabaseRepository {

    fun getAllProducts(): LiveData<List<CartProduct>>

    suspend fun addProduct(product: CartProduct)

    suspend fun deleteProduct(product: CartProduct)

    suspend fun deleteAllProducts()
}