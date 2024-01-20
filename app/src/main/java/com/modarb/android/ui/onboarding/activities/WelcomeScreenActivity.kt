package com.modarb.android.ui.onboarding.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.ActivityWelcomeScreenBinding


class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var bottomSheet: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        initBottomSheet()
        binding.loginTextView.setOnClickListener {
            if (!bottomSheet.isShowing) {
                showLogin()
            }
        }

        binding.startButton.setOnClickListener {
            val intent = Intent(this, OnBoardingSplashActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(this)

    }

    private fun showLogin() {
        bottomSheet.setContentView(R.layout.login_view)
        bottomSheet.show()
    }
}