package com.modarb.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.modarb.android.R
import com.modarb.android.databinding.ActivityHomeBinding
import com.modarb.android.ui.home.ui.more.activities.NotificationActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        navView.setupWithNavController(navController)

        val imageView = findViewById<View>(R.id.notificationBtn)
        imageView.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }


    fun navigateToFragment(itemId: Int) {
        binding.navView.selectedItemId = itemId
    }
}