package com.example.nikestore.feature.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.databinding.ActivityFavoriteProductBinding
import com.example.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class FavoriteProductActivity : NikeActivity(),FavoriteProductsAdapter.FavoriteProductEventListener {
    val viewModel:FavoriteProductViewModel by inject()
    private lateinit var binding:ActivityFavoriteProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infoBtn.setOnClickListener {
            Snackbar.make(it,R.string.favorite_help_message,Snackbar.LENGTH_LONG).show()
        }

        viewModel.favoriteLiveData.observe(this){
            if (it.isNotEmpty()){
                binding.favoritesRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                binding.favoritesRv.adapter=FavoriteProductsAdapter(it as MutableList<Product>,this,get())
            }else{
                showEmptyState(R.layout.view_default_empty_state)
                val emptyStateTv = findViewById<TextView>(R.id.emptyStateMessageTv)
                emptyStateTv.text=getString(R.string.favorite_empty_state_message)
            }
        }




    }

    override fun onclick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onDelete(product: Product) {
        viewModel.removeFromFavorite(product)
    }
}