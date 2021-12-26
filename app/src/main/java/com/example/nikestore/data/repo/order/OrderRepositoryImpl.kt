package com.example.nikestore.data.repo.order

import com.example.nikestore.data.dataclass.Checkout
import com.example.nikestore.data.dataclass.OrderHistoryItem
import com.example.nikestore.data.dataclass.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(val orderDataSource: OrderDataSource):OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(firstName,lastName,postalCode,phoneNumber, address, paymentMethod)
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return orderDataSource.checkout(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> = orderDataSource.list()
}