package com.example.nikestore.feature.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.formatPrice
import com.example.nikestore.databinding.ActivityCheckOutBinding
import com.example.nikestore.feature.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : NikeActivity() {
    private lateinit var binding: ActivityCheckOutBinding
    val viewModel:CheckoutViewModel by viewModel {
        val uri:Uri?=intent.data
        if (uri!=null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkoutLiveData.observe(this){
            binding.orderPriceTv.text = formatPrice(it.payable_price)
            binding.orderStatusTv.text=it.payment_status
            binding.purchaseStatusTv.text=if (it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق"
        }

        binding.orderHistoryChBtn.setOnClickListener {
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
    }
}