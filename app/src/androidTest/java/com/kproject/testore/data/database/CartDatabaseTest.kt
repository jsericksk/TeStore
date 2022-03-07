package com.kproject.testore.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kproject.testore.data.CartProduct
import com.kproject.testore.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class CartDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var cartDatabase: CartDatabase
    private lateinit var cartDAO: CartDAO

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
        hiltRule.inject()
        cartDAO = cartDatabase.cartDAO()
    }

    @After
    fun tearDown() {
        cartDatabase.close()
    }

    @Test
    fun addProduct_returnsSuccess() = runBlocking {
        cartDAO.addProduct(cartProduct)
        val products = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(products.contains(cartProduct)).isTrue()
    }

    @Test
    fun deleteProduct_returnsSuccess() = runBlocking {
        cartDAO.addProduct(cartProduct)
        cartDAO.deleteProduct(cartProduct)
        val products = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(products.isEmpty()).isTrue()
    }

    @Test
    fun deleteAllProducts_returnsSuccess() = runBlocking {
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

    @Test
    fun addTwoProductsWithTheSameId_returnsListWithOnlyOne() = runBlocking {
        cartDAO.addProduct(cartProduct)
        cartDAO.addProduct(cartProduct)
        val products = cartDAO.getAllProducts().getOrAwaitValue()
        assertThat(products.size).isEqualTo(1)
    }
}