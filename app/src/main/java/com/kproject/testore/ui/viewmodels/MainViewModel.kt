package com.kproject.testore.ui.viewmodels

import androidx.lifecycle.*
import com.kproject.testore.data.CartProduct
import com.kproject.testore.data.Product
import com.kproject.testore.data.Resource
import com.kproject.testore.data.repositories.CartDatabaseRepository
import com.kproject.testore.data.repositories.ProductRepository
import com.kproject.testore.utils.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartDatabaseRepository: CartDatabaseRepository
) : ViewModel() {
    private val _dataState = MutableLiveData<Resource<List<Product>>>()
    val dataState: LiveData<Resource<List<Product>>> = _dataState

    val allCartProduct = cartDatabaseRepository.getAllProducts()

    fun getProducts(category: ProductCategory) {
        _dataState.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = productRepository.getProducts(category)) {
                is Resource.Success -> {
                    _dataState.postValue(Resource.Success(result.data))
                }
                is Resource.Error -> {
                    _dataState.postValue(Resource.Error())
                }
                else -> {}
            }
        }
    }

    fun addProduct(product: CartProduct) {
        viewModelScope.launch {
            cartDatabaseRepository.addProduct(product)
        }
    }

    fun deleteProduct(product: CartProduct) {
        viewModelScope.launch {
            cartDatabaseRepository.deleteProduct(product)
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            cartDatabaseRepository.deleteAllProducts()
        }
    }
}