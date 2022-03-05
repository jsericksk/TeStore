package com.kproject.testore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartProduct(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val imageUrl: String,
    val rate: Float,
    val reviews: Int
)