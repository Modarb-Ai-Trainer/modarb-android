package com.modarb.android.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.modarb.android.R
import com.modarb.android.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        navView.setupWithNavController(navController)

        handleIntent()
    }

    private fun handleIntent() {
        val openNutrition = intent.getBooleanExtra("openNutrition", false)
        if (openNutrition) {
            navigateToFragment(R.id.navigation_nutrition)
        }
    }

    fun navigateToFragment(itemId: Int) {
        binding.navView.selectedItemId = itemId
    }
}