package com.kproject.testore.data.repository

import com.kproject.testore.data.Product
import com.kproject.testore.data.Resource
import com.kproject.testore.utils.ProductCategory

interface ProductRepository {
    suspend fun getProducts(category: ProductCategory): Resource<List<Product>>
}