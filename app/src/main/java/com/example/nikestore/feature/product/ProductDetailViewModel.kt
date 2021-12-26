package com.example.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.dataclass.Comment
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.repo.cart.CartRepository
import com.example.nikestore.data.repo.comment.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(bundle: Bundle, private val commentRepository: CommentRepository, val cartRepository: CartRepository) :
    NikeViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value=true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }
            })
    }

    fun onAddToCartBtn():Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}