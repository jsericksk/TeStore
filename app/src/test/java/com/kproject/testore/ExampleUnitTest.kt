package com.kproject.testore

import com.kproject.testore.data.Product
import com.kproject.testore.data.network.StoreApiRetrofitService
import com.kproject.testore.utils.ProductCategory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class ExampleUnitTest {

    @Test
    fun getAllProducts_returnsTrue() = runBlocking {
        try {
            val retrofitService = StoreApiRetrofitService.getInstance()
            val response: Response<List<Product>> =
                    retrofitService.getProductsByCategory(ProductCategory.ELECTRONICS.category)
            if (response.body() != null) {
                println("Lista de produtos: ${response.body()?.first()}")
            } else {
                println("Erro ao carregar lista de produtos")
            }
        } catch (e: Exception) {
            println("Erro desconhecido: ${e.message} \n${e.printStackTrace()}")
        }
    }
}