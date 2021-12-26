package com.example.nikestore.data.repo.banner

import com.example.nikestore.data.dataclass.Banner
import com.example.nikestore.sevices.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService): BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}