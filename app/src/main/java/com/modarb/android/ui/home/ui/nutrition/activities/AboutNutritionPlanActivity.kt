package com.modarb.android.ui.home.ui.nutrition.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivityAboutNutritionPlanBinding
import com.modarb.android.ui.helpers.NutritionHelper

class AboutNutritionPlanActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutNutritionPlanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutNutritionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent()
        handleBack()
    }


    private fun handleIntent() {
        val isAddProgram = intent.getBooleanExtra("isAddProgram", false)
        if (isAddProgram) {
            initIntentData()
            binding.choosePlanBtn.visibility = View.VISIBLE
        } else {
            initViews()
        }
    }

    private fun handleBack() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        binding.title.text = NutritionHelper.selectedMyProgram.level
        binding.desStandardTextView.text = NutritionHelper.selectedMyProgram.your_journey
        binding.desAboutThePlanTextView.text = NutritionHelper.selectedMyProgram.description

        val keyFeature: StringBuilder = StringBuilder()
        for (i in 0 until NutritionHelper.selectedMyProgram.key_features.size) {
            if (i == NutritionHelper.selectedMyProgram.key_features.size - 1) {
                keyFeature.append(NutritionHelper.selectedMyProgram.key_features[i].title + " : " + NutritionHelper.selectedMyProgram.key_features[i].description)
            } else {
                keyFeature.append(NutritionHelper.selectedMyProgram.key_features[i].title + " : " + NutritionHelper.selectedMyProgram.key_features[i].description + "\n\n")
            }
        }

        if (keyFeature.isNotEmpty()) binding.desKeyFeaturesTextView1.text = keyFeature
        if (NutritionHelper.selectedMyProgram.your_journey.isNotEmpty()) binding.desYourJourneyTextView.text =
            NutritionHelper.selectedMyProgram.your_journey
    }

    private fun initIntentData() {
        binding.title.text = NutritionHelper.selectedProgram.level
        binding.desStandardTextView.text = NutritionHelper.selectedProgram.your_journey
        binding.desAboutThePlanTextView.text = NutritionHelper.selectedProgram.description

        val keyFeature: StringBuilder = StringBuilder()
        for (i in 0 until NutritionHelper.selectedProgram.key_features.size) {
            if (i == NutritionHelper.selectedProgram.key_features.size - 1) {
                keyFeature.append(NutritionHelper.selectedProgram.key_features[i].title + " : " + NutritionHelper.selectedProgram.key_features[i].description)
            } else {
                keyFeature.append(NutritionHelper.selectedProgram.key_features[i].title + " : " + NutritionHelper.selectedProgram.key_features[i].description + "\n\n")
            }
        }

        if (keyFeature.isNotEmpty()) binding.desKeyFeaturesTextView1.text = keyFeature
        if (NutritionHelper.selectedProgram.your_journey.isNotEmpty()) binding.desYourJourneyTextView.text =
            NutritionHelper.selectedProgram.your_journey
    }
}