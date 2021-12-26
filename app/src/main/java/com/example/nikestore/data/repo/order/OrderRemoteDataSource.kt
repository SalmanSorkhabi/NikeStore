package com.example.nikestore.data.repo.order

import com.example.nikestore.data.dataclass.Checkout
import com.example.nikestore.data.dataclass.OrderHistoryItem
import com.example.nikestore.data.dataclass.SubmitOrderResult
import com.example.nikestore.sevices.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(val apiService: ApiService):OrderDataSource{
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name",firstName)
            addProperty("last_name",lastName)
            addProperty("postal_code",postalCode)
            addProperty("mobile",phoneNumber)
            addProperty("address",address)
            addProperty("payment_method",paymentMethod)
        })
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return apiService.checkout(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> = apiService.orders()
}