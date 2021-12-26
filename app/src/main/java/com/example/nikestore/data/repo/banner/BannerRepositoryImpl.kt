package com.example.nikestore.data.repo.banner

import com.example.nikestore.data.dataclass.Banner
import io.reactivex.Single

class BannerRepositoryImpl(var bannerRemoteDataSource: BannerDataSource) : BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}