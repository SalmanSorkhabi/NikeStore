package com.example.nikestore.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.dataclass.Comment
import com.example.nikestore.view.NikeToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    private val viewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        val commentListToolbar=findViewById<NikeToolbar>(R.id.commentListToolbar)


        viewModel.progressBarLiveData.observe(this){show->
            setProgressIndicator(show)
        }

        viewModel.commentsLiveData.observe(this) {
            val commentsRv = findViewById<RecyclerView>(R.id.commentsRv)
            val adapter = CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = adapter
        }

        commentListToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }
    }
}