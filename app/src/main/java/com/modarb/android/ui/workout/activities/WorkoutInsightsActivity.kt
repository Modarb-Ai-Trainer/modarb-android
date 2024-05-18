package com.modarb.android.ui.workout.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivityWorkoutInsightsBinding
import com.modarb.android.ui.home.HomeActivity

class WorkoutInsightsActivity : AppCompatActivity() {

    lateinit var binding: ActivityWorkoutInsightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutInsightsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        handleOnFinish()

        // TODO init adapter for post meals and design the item meal
    }


    private fun handleOnFinish() {
        binding.finistBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}