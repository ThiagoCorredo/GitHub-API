package com.tcorredo.githubapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        setupView()
    }

    private fun setupView() {
        mainBinding.bottomNavigation.apply {
            setupWithNavController(findNavController(R.id.fragment_container))
        }
    }
}