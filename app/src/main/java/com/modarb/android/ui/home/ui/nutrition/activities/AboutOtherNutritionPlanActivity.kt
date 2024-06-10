package com.modarb.android.ui.home.ui.nutrition.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R

class AboutOtherNutritionPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_other_nutrition_plan)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, NutritionOtherGuidePlansActivity::class.java)
            startActivity(intent)
        }
        val startButton: Button = findViewById(R.id.choosePlanButton)

        startButton.setOnClickListener {
            startActivity(Intent(this, NutritionOtherGuidePlansActivity::class.java))
        }
    }
}