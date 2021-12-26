package com.example.nikestore.data.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseDetail(
    var totalPrice: Int,
    var payablePrice: Int,
    var shippingCost: Int,
) : Parcelable