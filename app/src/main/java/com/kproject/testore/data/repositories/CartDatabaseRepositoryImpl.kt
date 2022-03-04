package com.kproject.testore.data.repositories

import androidx.lifecycle.LiveData
import com.kproject.testore.data.CartProduct
import com.kproject.testore.data.database.CartDAO
import javax.inject.Inject


class CartDatabaseRepositoryImpl @Inject constructor(
    private val cartDAO: CartDAO
) : CartDatabaseRepository {

    override fun getAllProducts(): LiveData<List<CartProduct>> {
        return cartDAO.getAllProducts()
    }

    override suspend fun addProduct(product: CartProduct) {
        cartDAO.addProduct(product)
    }

    override suspend fun deleteProduct(product: CartProduct) {
        cartDAO.deleteProduct(product)
    }

    override suspend fun deleteAllProducts() {
        cartDAO.deleteAllProducts()
    }
}