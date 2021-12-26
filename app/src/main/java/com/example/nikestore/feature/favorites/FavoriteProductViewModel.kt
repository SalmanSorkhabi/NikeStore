package com.example.nikestore.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.repo.product.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductViewModel(val productRepository: ProductRepository):NikeViewModel() {

    val favoriteLiveData=MutableLiveData<List<Product>>()
    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    favoriteLiveData.postValue(t)
                }
            })
    }

    fun removeFromFavorite(product: Product){
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("removeFromFavorite completed")
                }
            })
    }

}