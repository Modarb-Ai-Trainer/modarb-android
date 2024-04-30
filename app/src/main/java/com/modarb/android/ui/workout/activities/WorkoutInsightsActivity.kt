package com.modarb.android.ui.workout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivityWorkoutInsightsBinding

class WorkoutInsightsActivity : AppCompatActivity() {

    lateinit var binding: ActivityWorkoutInsightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutInsightsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // TODO init adapter for post meals and design the item meal
    }
}