package com.kproject.testore.data.network

import com.kproject.testore.data.Product
import com.kproject.testore.utils.Constants
import com.kproject.testore.utils.ProductCategory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApiRetrofitService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/category/{productCategory}")
    suspend fun getProductsByCategory(@Path("productCategory") productCategory: String): Response<List<Product>>

    companion object {
        var retrofitService: StoreApiRetrofitService? = null

        fun getInstance() : StoreApiRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(StoreApiRetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}