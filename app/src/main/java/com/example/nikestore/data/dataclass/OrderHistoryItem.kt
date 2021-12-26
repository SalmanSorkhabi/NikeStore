package com.example.nikestore.data.dataclass

data class OrderHistoryItem(
    val id: Int,
    val order_items: List<OrderItem>,
    val payable: Int
)