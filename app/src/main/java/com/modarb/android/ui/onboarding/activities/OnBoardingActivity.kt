package com.modarb.android.ui.onboarding.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R
import com.modarb.android.databinding.ActivityOnBoardingBinding
import com.modarb.android.ui.onboarding.adapters.OnBoardingAdapter


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private var currPos: Int = 0
    private var curPage: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewPager()
        handleButtons()
        handleOnBack()

    }

    @SuppressLint("InflateParams")
    private fun initViewPager() {
        val view1 = layoutInflater.inflate(R.layout.single_selection_view, null)
        val view2 = layoutInflater.inflate(R.layout.gender_selection_view, null)
        val view3 = layoutInflater.inflate(R.layout.target_weight_selection_view, null)
        val view4 = layoutInflater.inflate(R.layout.message_view, null)
        val view5 = layoutInflater.inflate(R.layout.single_selection_view, null)
        val view6 = layoutInflater.inflate(R.layout.single_selection_view, null)
        val view7 = layoutInflater.inflate(R.layout.multiple_selection_view, null)
        val view8 = layoutInflater.inflate(R.layout.multiple_selection_view, null)
        val view9 = layoutInflater.inflate(R.layout.message_view, null)

        val adapter =
            OnBoardingAdapter(
                listOf(view1, view2, view3, view4, view5, view6, view7, view8, view9),
                this
            )
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
    }

    private fun handleButtons() {
        binding.nextButton.setOnClickListener {
            goNext()
        }

        binding.backButton.setOnClickListener {
            goBack()
        }

    }

    private fun handleOnBack() {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
    }

    private fun goNext() {
        currPos = binding.viewPager.currentItem
        if ((currPos + 1) != binding.viewPager.adapter?.itemCount) {
            binding.viewPager.currentItem = currPos + 1
            curPage++
        }
    }

    private fun goBack() {
        currPos = binding.viewPager.currentItem
        if (curPage == 0) finish()
        if ((currPos - 1) != binding.viewPager.adapter?.itemCount) {
            binding.viewPager.currentItem = currPos - 1
            curPage--
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && curPage == 0) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }


}