package com.modarb.android.ui.onboarding.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R
import com.modarb.android.databinding.ActivityOnBoardingSplashBinding
import com.modarb.android.ui.onboarding.adapters.SplashViewPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

@SuppressLint("CustomSplashScreen")
class OnBoardingSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingSplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewPager()
    }

    private fun initViewPager() {
        val view1 = layoutInflater.inflate(R.layout.onboarding1, null)
        val view2 = layoutInflater.inflate(R.layout.onboarding2, null)
        val view3 = layoutInflater.inflate(R.layout.onboarding3, null)
        val adapter = SplashViewPagerAdapter(listOf(view1, view2, view3))
        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        //binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
        dotsIndicator.attachTo(binding.viewPager)
        handleButtons()
    }

    private fun handleButtons() {
        binding.nextButton.setOnClickListener {
            val currPos: Int = binding.viewPager.currentItem
            if ((currPos + 1) != binding.viewPager.adapter?.itemCount) {
                binding.viewPager.currentItem = currPos + 1
            } else {
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }


    }
}