package com.example.nikestore.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.data.dataclass.PurchaseDetail
import com.example.nikestore.R
import com.example.nikestore.common.EnglishConverter
import com.example.nikestore.common.formatPrice
import com.example.nikestore.data.dataclass.CartItem
import com.example.nikestore.sevices.ImageLoadingService
import com.example.nikestore.view.NikeImageView

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1

class CartItemAdapter(
    val cartItems: MutableList<CartItem>,
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallbacks: CartItemViewCallbacks
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null


    inner class CartItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productIv = itemView.findViewById<NikeImageView>(R.id.productIv)
        private val increaseBtn = itemView.findViewById<ImageView>(R.id.increaseBtn)
        private val decreaseBtn = itemView.findViewById<ImageView>(R.id.decreaseBtn)
        private val productTitleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        private val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPriceTv)
        private val removeFromCartBtn = itemView.findViewById<TextView>(R.id.removeFromCartBtn)
        private val priceTv = itemView.findViewById<TextView>(R.id.priceTv)
        private val cartItemCountTv = itemView.findViewById<TextView>(R.id.countItemCartTv)
        private val changeCountProgressBar =
            itemView.findViewById<ProgressBar>(R.id.changeCountProgressBar)

        fun bindCartItem(cartItem: CartItem) {
            imageLoadingService.load(productIv, cartItem.product.image)
            productTitleTv.text = cartItem.product.title
            priceTv.text = formatPrice(cartItem.product.price)
            previousPriceTv.text =
                formatPrice(cartItem.product.previous_price + cartItem.product.discount)
            previousPriceTv.paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            cartItemCountTv.text = cartItem.count.toString()
            removeFromCartBtn.setOnClickListener {
                cartItemViewCallbacks.onRemoveCartItemButtonClick(cartItem)
            }

            changeCountProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

            cartItemCountTv.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            increaseBtn.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                changeCountProgressBar.visibility = View.VISIBLE
                cartItemCountTv.visibility = View.INVISIBLE
                cartItemViewCallbacks.onIncreaseCartItemButtonClick(cartItem)
            }

            decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    changeCountProgressBar.visibility = View.VISIBLE
                    cartItemCountTv.visibility = View.INVISIBLE
                    cartItemViewCallbacks.onDecreaseCartItemButtonClick(cartItem)
                }
            }

            productIv.setOnClickListener {
                cartItemViewCallbacks.onProductImageClick(cartItem)
            }

            if (cartItem.changeCountProgressBarIsVisible) {
                changeCountProgressBar.visibility = View.VISIBLE
                increaseBtn.visibility = View.GONE
            }
        }
    }

    class PurchaseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val totalPrice: TextView =
            itemView.findViewById(R.id.totalPriceTv)
        private val payablePrice: TextView =
            itemView.findViewById(R.id.payablePriceTv)
        private val shippingCost: TextView =
            itemView.findViewById(R.id.shippingCostTv)

        fun bindPurchaseItem(purchaseDetail: PurchaseDetail) {
            totalPrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.totalPrice).toString()
                )
            payablePrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.payablePrice).toString()
                )
            shippingCost.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.shippingCost).toString()
                )
        }
    }

    interface CartItemViewCallbacks {
        fun onRemoveCartItemButtonClick(cartItem: CartItem)
        fun onIncreaseCartItemButtonClick(cartItem: CartItem)
        fun onDecreaseCartItemButtonClick(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CART_ITEM)
            return CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_cart, parent, false
                )
            )
        else
            return PurchaseItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_purchase_details, parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder)
            holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseItemViewHolder) {
            purchaseDetail?.let { detail ->
                holder.bindPurchaseItem(detail)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)
            return VIEW_TYPE_PURCHASE_DETAILS
        else
            return VIEW_TYPE_CART_ITEM
    }

    fun removeCartItem(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }
}