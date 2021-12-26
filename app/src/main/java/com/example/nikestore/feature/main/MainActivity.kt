package com.example.nikestore.feature.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.convertDpToPixel
import com.example.nikestore.data.dataclass.CartItemCount
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.cart, R.id.profile)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
        val bottomNavigationMain = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val badge = bottomNavigationMain.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        badge.backgroundColor = MaterialColors.getColor(bottomNavigationMain, R.attr.colorPrimary)
        badge.number = cartItemCount.count
        badge.verticalOffset= convertDpToPixel(10f,this).toInt()
        badge.isVisible = cartItemCount.count > 0
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemCount()
    }
}