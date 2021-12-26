package com.example.nikestore.data.repo.comment

import com.example.nikestore.data.dataclass.Comment
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource): CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}