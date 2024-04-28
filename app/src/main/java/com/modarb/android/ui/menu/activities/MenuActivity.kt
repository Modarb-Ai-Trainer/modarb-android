package com.modarb.android.ui.menu.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.modarb.android.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val cardView = findViewById<View>(R.id.cardView)
        cardView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        val cardView2 = findViewById<View>(R.id.cardView5)
        cardView2.setOnClickListener {
            startActivity(Intent(this, ReminderActivity::class.java))
        }
        val cardView3 = findViewById<View>(R.id.cardView6)
        cardView3.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}