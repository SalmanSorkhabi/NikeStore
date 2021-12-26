package com.example.nikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.common.convertDpToPixel
import com.example.nikestore.data.dataclass.Product
import com.example.nikestore.data.dataclass.SORT_LATEST
import com.example.nikestore.feature.common.ProductListAdapter
import com.example.nikestore.feature.common.ProductPopularListAdapter
import com.example.nikestore.feature.common.VIEW_TYPE_ROUND
import com.example.nikestore.feature.list.ProductListActivity
import com.example.nikestore.feature.main.BannerSliderAdapter
import com.example.nikestore.feature.product.ProductDetailActivity
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(), ProductListAdapter.ProductEventListener,
    ProductPopularListAdapter.OnProductClickListener {

    val homeViewModel: HomeViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ROUND)}
    val productPopularListAdapter: ProductPopularListAdapter by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.latestProductRv)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = productListAdapter


        val popularRecyclerView: RecyclerView = view.findViewById(R.id.popularProductRv)
        popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        popularRecyclerView.adapter = productPopularListAdapter

        productListAdapter.productEventListener = this
        productPopularListAdapter.productEventListener = this





        homeViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.productsPopularLiveData.observe(viewLifecycleOwner) {
            Timber.i("ProductPopular-->> ${it}")
            productPopularListAdapter.products = it as ArrayList<Product>
        }

        val viewLatestProductBtn=view.findViewById<Button>(R.id.viewLatestProductBtn)
        viewLatestProductBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        homeViewModel.bannersLiveData.observe(viewLifecycleOwner) {
            val bannerSliderViewPager = view.findViewById<ViewPager2>(R.id.viewPager_main_slider)
            val sliderIndicator = view.findViewById<SpringDotsIndicator>(R.id.sliderIndicator)
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter
            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()
            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams
            sliderIndicator.setViewPager2(bannerSliderViewPager)


        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireActivity(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorite(product)
    }
}