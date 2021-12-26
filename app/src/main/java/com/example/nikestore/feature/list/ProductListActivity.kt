package com.example.nikestore.feature.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.feature.common.ProductListAdapter
import com.example.nikestore.feature.common.VIEW_TYPE_LARGE
import com.example.nikestore.feature.common.VIEW_TYPE_SMALL
import com.example.nikestore.feature.product.ProductDetailActivity
import com.example.nikestore.view.NikeToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(),ProductListAdapter.ProductEventListener {
    val viewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }

    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val productsRv = findViewById<RecyclerView>(R.id.productsRv)

        val gridLayoutManager = GridLayoutManager(this, 2)
        productsRv.layoutManager = gridLayoutManager
        productsRv.adapter = productListAdapter
        productListAdapter.productEventListener = this

        val viewTypeChangerBtn = findViewById<ImageView>(R.id.viewTypeChangerBtn)
        viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        viewModel.selectedSortTitleLiveData.observe(this){
            val sortTitleTv=findViewById<TextView>(R.id.selectedSortTitleTv)
            sortTitleTv.text=getString(it)
        }

        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        viewModel.productLiveData.observe(this) { productList ->
            Timber.i(productList.toString())
            productListAdapter.products = productList as ArrayList<Product>
        }

        val toolbarView=findViewById<NikeToolbar>(R.id.toolbarView)
        toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        val sortBtn = findViewById<View>(R.id.sortBtn)
        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray,
                    viewModel.sort)
                { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSelectedSortChangeByUser(selectedSortIndex)
                }.setTitle(R.string.sort)
            dialog.show()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        viewModel.addProductToFavorite(product)
    }
}