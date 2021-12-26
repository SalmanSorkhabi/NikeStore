package com.example.nikestore.feature.main

import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.dataclass.CartItemCount
import com.example.nikestore.data.dataclass.TokenContainer
import com.example.nikestore.data.repo.cart.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository):NikeViewModel() {
    fun getCartItemCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }
}