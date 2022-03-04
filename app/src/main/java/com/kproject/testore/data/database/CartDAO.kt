package com.kproject.testore.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kproject.testore.data.CartProduct

@Dao
interface CartDAO {

    @Query("SELECT * FROM cart_table")
    fun getAllProducts(): LiveData<List<CartProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: CartProduct)

    @Delete
    suspend fun deleteProduct(product: CartProduct)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllProducts()
}