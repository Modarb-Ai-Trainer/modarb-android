package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivityWorkoutInsightsBinding
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.HomeActivity

class WorkoutInsightsActivity : AppCompatActivity() {

    lateinit var binding: ActivityWorkoutInsightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutInsightsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
        handleOnFinish()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.exerciseCount.text =
            "${WorkoutData.getTodayWorkout()!!.exercises.size} \n Exercises"
        var totalDuration = 0
        WorkoutData.getTodayWorkout()!!.exercises.forEach {
            totalDuration += it.duration
            totalDuration += it.expectedDurationRange.min
        }
        binding.dayDetails.text =
            " Day" + WorkoutData.getTodayWorkout()!!.day_number + " " + WorkoutData.getTodayWorkout()!!.day_type
        binding.timeTxt.text = "${(totalDuration / 60)} \n Minutes"

    }


    private fun handleOnFinish() {
        binding.finistBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}