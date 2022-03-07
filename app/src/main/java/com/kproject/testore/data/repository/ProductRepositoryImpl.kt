package com.kproject.testore.data.repository

import com.kproject.testore.data.Product
import com.kproject.testore.data.Resource
import com.kproject.testore.data.network.StoreApiRetrofitService
import com.kproject.testore.utils.ProductCategory

class ProductRepositoryImpl : ProductRepository {

    override suspend fun getProducts(category: ProductCategory): Resource<List<Product>> {
        return try {
            val retrofitService = StoreApiRetrofitService.getInstance()

            val response = if (category == ProductCategory.ALL) {
                retrofitService.getAllProducts()
            } else {
                retrofitService.getProductsByCategory(category.category)
            }
            response.body()?.let { products ->
                Resource.Success(products)
            } ?: Resource.Error()
        } catch (e: Exception) {
            Resource.Error()
        }
    }
}