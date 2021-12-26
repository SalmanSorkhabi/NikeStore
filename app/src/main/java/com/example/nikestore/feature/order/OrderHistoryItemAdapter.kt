package com.example.nikestore.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.animation.content.Content
import com.example.nikestore.R
import com.example.nikestore.common.convertDpToPixel
import com.example.nikestore.common.formatPrice
import com.example.nikestore.data.dataclass.OrderHistoryItem
import com.example.nikestore.view.NikeImageView

class OrderHistoryItemAdapter(val context:Context,val orderHistoryItems:List<OrderHistoryItem>):
    RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>() {
    val layoutParams:LinearLayout.LayoutParams

    init {
        val size = convertDpToPixel(100f,context).toInt()
        val margin = convertDpToPixel(8f,context).toInt()
        layoutParams=LinearLayout.LayoutParams(size,size)
        layoutParams.setMargins(margin,0,margin,0)
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val orderIdTv = itemView.findViewById<TextView>(R.id.orderIdTv)
        val orderAmountTv = itemView.findViewById<TextView>(R.id.orderAmountTv)
        val orderProductLl = itemView.findViewById<LinearLayout>(R.id.orderProductLl)
        fun bind(orderHistoryItem: OrderHistoryItem){
            orderIdTv.text=orderHistoryItem.id.toString()
            orderAmountTv.text= formatPrice(orderHistoryItem.payable)
            orderProductLl.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView =NikeImageView(context)
                imageView.layoutParams=layoutParams
                imageView.setImageURI(it.product.image)
                orderProductLl.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(orderHistoryItems[position])

    override fun getItemCount(): Int = orderHistoryItems.size
}