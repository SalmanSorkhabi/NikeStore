package com.example.nikestore.data.repo.product

import androidx.room.*
import com.example.nikestore.data.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE

@Dao
interface ProductLocalDatSource: ProductDataSource {
    override fun getProducts(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProducts(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(product: Product): Completable

    @Delete
    override fun deleteFromFavorites(product: Product): Completable
}