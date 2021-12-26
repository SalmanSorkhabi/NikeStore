package com.example.nikestore.data.repo.product

import com.example.nikestore.data.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDatSource
): ProductRepository {
    override fun getProducts(sort: Int): Single<List<Product>> =
        localDataSource.getFavoriteProducts()
            .flatMap { favoriteProduct ->
                remoteDataSource.getProducts(sort).doOnSuccess {
                    val favoriteProductIts = favoriteProduct.map {
                        it.id
                    }
                    it.forEach { product ->
                        if (favoriteProductIts.contains(product.id))
                            product.isFavorite=true
                    }
                }
            }

    override fun getFavoriteProducts(): Single<List<Product>> = localDataSource.getFavoriteProducts()

    override fun addToFavorites(product: Product): Completable = localDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable = localDataSource.deleteFromFavorites(product)
}