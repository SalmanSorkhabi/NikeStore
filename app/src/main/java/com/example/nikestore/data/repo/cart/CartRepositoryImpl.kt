package com.example.nikestore.data.repo.cart

import com.example.nikestore.data.dataclass.AddToCartResponse
import com.example.nikestore.data.dataclass.CartItemCount
import com.example.nikestore.data.dataclass.CartResponse
import com.example.nikestore.data.dataclass.MessageResponse
import io.reactivex.Single

class CartRepositoryImpl(val remoteDataSource: CartDataSource): CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = remoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> = remoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> = remoteDataSource.changeCount(cartItemId,count)

    override fun getCartItemsCount(): Single<CartItemCount> = remoteDataSource.getCartItemsCount()
}