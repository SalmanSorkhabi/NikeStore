package com.example.nikestore.data.repo.comment

import com.example.nikestore.data.dataclass.Comment
import com.example.nikestore.sevices.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(private val apiService: ApiService): CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> = apiService.getComment(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}