package com.modarb.android.ui.home.ui.nutrition.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.modarb.android.databinding.ActivityAboutNutritionPlanBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.helpers.NutritionHelper
import com.modarb.android.ui.home.HomeActivity
import com.modarb.android.ui.home.ui.nutrition.PlanBody
import com.modarb.android.ui.home.ui.nutrition.presentation.NutritionViewModel
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.launch

class AboutNutritionPlanActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutNutritionPlanBinding
    lateinit var viewModel: NutritionViewModel
    lateinit var planId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutNutritionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        handleIntent()
        handleBack()
    }

    private fun handleEnrollButton() {
        binding.choosePlanBtn.setOnClickListener {
            Log.d("planId", "handleEnrollButton: $planId")
            viewModel.enrollIntoPlan(
                "Bearer ${UserPrefUtil.getUserData(this@AboutNutritionPlanActivity)!!.token}",
                PlanBody(planId)
            )
        }

        lifecycleScope.launch {
            viewModel.enrollIntoPlan.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        Toast.makeText(
                            this@AboutNutritionPlanActivity,
                            "Enrolled Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent(this@AboutNutritionPlanActivity, HomeActivity::class.java)
                        i.putExtra("openNutrition", true)
                        startActivity(i)
                        finish()
                    }

                    is ApiResult.Error -> {
                        Toast.makeText(
                            this@AboutNutritionPlanActivity, it.data.message, Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleFail(exception: Throwable) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NutritionViewModel::class.java]
    }


    private fun handleIntent() {
        val isAddProgram = intent.getBooleanExtra("isAddProgram", false)
        planId = intent.getStringExtra("planId").toString()
        if (isAddProgram) {
            initIntentData()
            handleEnrollButton()
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
        else binding.keyFeaturesTextView.visibility = View.GONE
        if (NutritionHelper.selectedMyProgram.your_journey != null) binding.desYourJourneyTextView.text =
            NutritionHelper.selectedMyProgram.your_journey
        else binding.yourJourneyTextView.visibility = View.GONE
    }

    private fun initIntentData() {
        //TODO refactor this
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