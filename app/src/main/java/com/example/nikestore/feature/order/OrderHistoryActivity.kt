package com.example.nikestore.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.databinding.ActivityOrderHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivity() {
    val viewModel : OrderHistoryViewModel by viewModel()
    private lateinit var binding: ActivityOrderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        binding.orderHistoryRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        viewModel.orders.observe(this){
            binding.orderHistoryRv.adapter=OrderHistoryItemAdapter(this,it)
        }

        binding.toolbarView.onBackButtonClickListener= View.OnClickListener {
            finish()
        }
    }
}