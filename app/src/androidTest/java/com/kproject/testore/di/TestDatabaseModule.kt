package com.kproject.testore.di

import android.content.Context
import androidx.room.Room
import com.kproject.testore.data.database.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Provides
    @Named("test_database")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, CartDatabase::class.java)
                .allowMainThreadQueries()
                .build()
}