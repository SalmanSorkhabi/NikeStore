package com.example.nikestore.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nikestore.R
import com.example.nikestore.databinding.ActivityAuthBinding
import com.example.nikestore.databinding.ActivityShippingBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,LoginFragment())
        }.commit()
    }
}