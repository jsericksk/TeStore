package com.kproject.testore.data

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val image: String,
    val rating: Rating
) : Serializable

data class Rating(
    val rate: Float,
    val count: Int
)