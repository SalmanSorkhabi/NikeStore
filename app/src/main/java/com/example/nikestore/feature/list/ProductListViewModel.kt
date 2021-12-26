package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.asyncNetworkRequest
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.repo.product.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    private fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }
            })
    }


    fun onSelectedSortChangeByUser(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProducts()
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