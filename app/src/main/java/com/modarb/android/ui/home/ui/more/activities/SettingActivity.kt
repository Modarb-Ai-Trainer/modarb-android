package com.modarb.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // TODO refactor
        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            finish()
        }
        val cardView = findViewById<View>(R.id.appAppearanceCardView)
        cardView.setOnClickListener {
            startActivity(Intent(this, AppAppearanceActivity::class.java))
        }
    }
}