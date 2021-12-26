package com.example.nikestore.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.dataclass.Banner
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.dataclass.SORT_LATEST
import com.example.nikestore.data.dataclass.SORT_POPULAR
import com.example.nikestore.data.repo.banner.BannerRepository
import com.example.nikestore.data.repo.product.ProductRepository
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val productRepository: ProductRepository,
    bannerRepository: BannerRepository
) : NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val productsPopularLiveData = MutableLiveData<List<Product>>()
    val bannersLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProducts(SORT_LATEST)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })

        productRepository.getProducts(SORT_POPULAR)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsPopularLiveData.value = t
                }
            })

        bannerRepository.getBanners()
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }

    fun addProductToFavorite(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }
}







