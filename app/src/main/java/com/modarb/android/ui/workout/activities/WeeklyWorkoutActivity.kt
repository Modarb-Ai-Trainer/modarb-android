package com.modarb.android.ui.workout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.modarb.android.databinding.ActivityWeeklyWorkoutBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.workout.adapters.WorkoutAdapter

class WeeklyWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeeklyWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeeklyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecycleData()
    }

    private fun setRecycleData() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout())
        binding.recyclerView.adapter = adapter
    }

}
