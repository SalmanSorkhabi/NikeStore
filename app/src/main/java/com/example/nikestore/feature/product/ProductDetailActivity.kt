package com.example.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.*
import com.example.nikestore.data.dataclass.Comment
import com.example.nikestore.feature.product.comment.CommentAdapter
import com.example.nikestore.feature.product.comment.CommentListActivity
import com.example.nikestore.sevices.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import com.example.nikestore.view.scroll.ObservableScrollView
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {
    private val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productIv: NikeImageView = findViewById(R.id.productIv)
        val titleTv: TextView = findViewById(R.id.titleTv)
        val toolbarTitleTv: TextView = findViewById(R.id.toolbarTitleTv)
        val currentPriceTV: TextView = findViewById(R.id.currentPriceTv)
        val previousPriceTv: TextView = findViewById(R.id.previousPriceTv)
        val viewAllCommentBtn: Button = findViewById(R.id.viewAllCommentBtn)
        val toolbarView: ImageView = findViewById(R.id.backBtn)

        toolbarView.setOnClickListener {
            finish()
        }

        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIv, it.image)
            titleTv.text = it.title
            toolbarTitleTv.text = it.title
            currentPriceTV.text = formatPrice(it.price)
            previousPriceTv.text = formatPrice(it.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        productDetailViewModel.progressBarLiveData.observe(this) { show ->
            setProgressIndicator(show)
        }
        productDetailViewModel.commentLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3)
                viewAllCommentBtn.visibility = View.VISIBLE
            viewAllCommentBtn.setOnClickListener {
                val intent = Intent(this, CommentListActivity::class.java)
                intent.putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                startActivity(intent)
            }
        }

        initViews()


    }


    private fun initViews() {
        val observableScrollView: ObservableScrollView = findViewById(R.id.observableScrollView)
        val commentRecyclerView = findViewById<RecyclerView>(R.id.commentsRv)
        val productIv: NikeImageView = findViewById(R.id.productIv)
        val addToCartBtn:ExtendedFloatingActionButton =findViewById(R.id.addToCartBtn)



        commentRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.isNestedScrollingEnabled = false



        productIv.post {
            val productIvHeight = productIv.height
            val toolbar = findViewById<MaterialCardView>(R.id.toolbarView)
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    Timber.i("ProductIv Height is -->> $productIvHeight")
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productIv.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

        addToCartBtn.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }
                })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}