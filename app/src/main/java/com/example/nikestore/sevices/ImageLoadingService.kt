package com.example.nikestore.sevices

import com.example.nikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView, imageUrl: String)
}