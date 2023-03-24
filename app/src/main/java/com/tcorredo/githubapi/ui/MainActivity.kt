package com.tcorredo.githubapi.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.databinding.ActivityMainBinding
import com.tcorredo.githubapi.ui.project.ProjectFragment

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