package com.kproject.testore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kproject.testore.data.CartProduct

@Database(entities = [CartProduct::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDAO(): CartDAO
}