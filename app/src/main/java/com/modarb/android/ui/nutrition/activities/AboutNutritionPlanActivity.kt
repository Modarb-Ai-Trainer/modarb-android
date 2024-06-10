package com.modarb.android.ui.nutrition.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.modarb.android.R

class AboutNutritionPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_nutrition_plan)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, NutritionGuidePlansActivity::class.java)
            startActivity(intent)
        }
    }
}