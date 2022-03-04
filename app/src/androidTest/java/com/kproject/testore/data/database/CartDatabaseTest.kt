package com.kproject.testore.data.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kproject.testore.data.CartProduct
import com.kproject.testore.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cartDAO: CartDAO
    private lateinit var db: CartDatabase

    private val cartProduct = CartProduct(
        id = 0,
        title = "Camisa Masculina",
        price = 150f,
        description = "Camisa masculina para adultos.",
        imageUrl = "http://test.com/camisa_masculina.jpg",
        rate = 3.5f,
        reviews = 300
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CartDatabase::class.java).build()
        cartDAO = db.cartDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addAnProduct_returnsTrue() = runBlocking {
        cartDAO.addProduct(cartProduct)
        val products = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(products.contains(cartProduct)).isTrue()
    }

    @Test
    fun deleteProduct_returnsTrue() = runBlocking {
        cartDAO.addProduct(cartProduct)
        cartDAO.deleteProduct(cartProduct)
        val products = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(products.isEmpty()).isTrue()
    }

    @Test
    fun deleteAllProducts_returnTrue() = runBlocking {
        for (i in 1..10) {
            val product = CartProduct(
                id = i,
                title = "Camisa Masculina",
                price = 150f,
                description = "Camisa masculina para adultos.",
                imageUrl = "http://test.com/camisa_masculina.jpg",
                rate = 3.5f,
                reviews = (6 * i)
            )
            cartDAO.addProduct(product)
        }

        val currentList = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(currentList.size).isEqualTo(10)
        cartDAO.deleteAllProducts()
        val newList = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(newList.isEmpty()).isTrue()
    }


}