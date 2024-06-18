package com.modarb.android.ui.home.ui.nutrition.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivityAboutNutritionPlanBinding
import com.modarb.android.ui.helpers.NutritionHelper

class AboutNutritionPlanActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutNutritionPlanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutNutritionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        handleBack()
    }

    private fun handleBack() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        binding.desStandardTextView.text = NutritionHelper.selectedMeal.level
        binding.desAboutThePlanTextView.text = NutritionHelper.selectedMeal.description

        val keyFeature: StringBuilder = StringBuilder()
        for (i in 0 until NutritionHelper.selectedMeal.key_features.size) {
            if (i == NutritionHelper.selectedMeal.key_features.size - 1) {
                keyFeature.append(NutritionHelper.selectedMeal.key_features[i].title + " : " + NutritionHelper.selectedMeal.key_features[i].description)
            } else {
                keyFeature.append(NutritionHelper.selectedMeal.key_features[i].title + " : " + NutritionHelper.selectedMeal.key_features[i].description + "\n\n")
            }
        }

        if (keyFeature.isNotEmpty()) binding.desKeyFeaturesTextView1.text = keyFeature
        if (NutritionHelper.selectedMeal.your_journey.isNotEmpty()) binding.desYourJourneyTextView.text =
            NutritionHelper.selectedMeal.your_journey
    }
}