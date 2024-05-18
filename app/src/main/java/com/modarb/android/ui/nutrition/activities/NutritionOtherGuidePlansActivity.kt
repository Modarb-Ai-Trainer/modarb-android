package com.modarb.android.ui.nutrition.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.nutrition.adapters.AvailablePlanAdapter
import com.modarb.android.ui.nutrition.models.AvailablePlanModel

class NutritionOtherGuidePlansActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_other_guide_plans)

        val availablePlansList = listOf(
            AvailablePlanModel(R.drawable.available_plan_img1, "Intermittent fasting", "20 Days", 1),
            AvailablePlanModel(R.drawable.available_plan_img2, "GreenGlow Wellness", "20 Days", 2),
            AvailablePlanModel(R.drawable.available_plan_img3, "LowCarb Harmony", "20 Days", 3),
            AvailablePlanModel(R.drawable.available_plan_img4, "VeggieVerve Vitality", "20 Days", 4)
        )

        val recyclerView: RecyclerView = findViewById(R.id.availablePlansRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AvailablePlanAdapter(availablePlansList) { position ->
            if (position == 0) {
                val intent = Intent(this, AboutOtherNutritionPlanActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
