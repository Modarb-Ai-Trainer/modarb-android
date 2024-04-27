package com.modarb.android.ui.menu.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.modarb.android.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        val cardView = findViewById<View>(R.id.appAppearanceCardView)
        cardView.setOnClickListener {
            startActivity(Intent(this, AppAppearanceActivity::class.java))
        }
    }
}