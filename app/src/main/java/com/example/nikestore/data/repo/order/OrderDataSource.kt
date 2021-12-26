package com.example.nikestore.data.repo.order

import com.example.nikestore.data.dataclass.Checkout
import com.example.nikestore.data.dataclass.OrderHistoryItem
import com.example.nikestore.data.dataclass.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {
    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod:String
    ):Single<SubmitOrderResult>

    fun checkout(orderId:Int):Single<Checkout>

    fun list():Single<List<OrderHistoryItem>>
}