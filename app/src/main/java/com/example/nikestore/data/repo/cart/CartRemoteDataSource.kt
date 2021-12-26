package com.example.nikestore.data.repo.cart

import com.example.nikestore.data.dataclass.AddToCartResponse
import com.example.nikestore.data.dataclass.CartItemCount
import com.example.nikestore.data.dataclass.CartResponse
import com.example.nikestore.data.dataclass.MessageResponse
import com.example.nikestore.sevices.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService): CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productId)
        }
    )

    override fun get(): Single<CartResponse> = apiService.getCart()


    override fun remove(cartItemId: Int): Single<MessageResponse> =apiService.removeItemFromCart(
        JsonObject().apply {
            addProperty("cart_item_id",cartItemId)
        }
    )

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> = apiService.changeCart(JsonObject().apply {
        addProperty("cart_item_id",cartItemId)
        addProperty("count",count)
    })

    override fun getCartItemsCount(): Single<CartItemCount> = apiService.getCartItemsCount()
}