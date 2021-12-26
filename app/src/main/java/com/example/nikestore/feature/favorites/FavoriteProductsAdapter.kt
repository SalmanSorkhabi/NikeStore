package com.example.nikestore.feature.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.sevices.ImageLoadingService
import com.example.nikestore.view.NikeImageView

class FavoriteProductsAdapter(
    private val products:MutableList<Product>,
    val favoriteProductEventListener: FavoriteProductEventListener,
    private val imageLoadingService: ImageLoadingService):
    RecyclerView.Adapter<FavoriteProductsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        private val productIv = itemView.findViewById<NikeImageView>(R.id.productFavoriteIv)
        private val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
        private val deleteBtn = itemView.findViewById<ImageView>(R.id.deletBtn)
        fun bindProduct(product:Product){
            imageLoadingService.load(productIv,product.image)
            titleTv.text=product.title
            itemView.setOnClickListener {
                favoriteProductEventListener.onclick(product)
            }
            deleteBtn.setOnClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onDelete(product)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    interface FavoriteProductEventListener{
        fun onclick(product: Product)
        fun onDelete(product: Product)
    }
}