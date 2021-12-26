package com.example.nikestore.feature.common

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.formatPrice
import com.example.nikestore.common.implementSpringAnimationTrait
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.sevices.ImageLoadingService
import com.example.nikestore.view.NikeImageView

class ProductPopularListAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductPopularListAdapter.ViewHolder>() {

    var productEventListener: ProductListAdapter.ProductEventListener? = null

    var products = ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)

        @SuppressLint("SetTextI18n")
        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv,product.image)
            titleTv.text=product.title
            currentPriceTv.text= formatPrice(product.price)
            previousPriceTv.text= formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productEventListener?.onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

}