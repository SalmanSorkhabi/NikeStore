package com.example.nikestore.data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.repo.product.ProductLocalDatSource

@Database(entities = [Product::class], version = 1 , exportSchema = false)
abstract class AppDataBase:RoomDatabase() {
    abstract fun productDao():ProductLocalDatSource
}