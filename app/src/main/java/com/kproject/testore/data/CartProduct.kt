package com.kproject.testore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Não adicionado o campo de avaliação para futura migração
 * e testes no banco de dados.
 */
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