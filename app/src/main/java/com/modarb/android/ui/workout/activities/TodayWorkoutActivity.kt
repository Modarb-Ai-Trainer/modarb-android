package com.modarb.android.ui.workout.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.modarb.android.R
import com.modarb.android.databinding.ActivityTodayWorkoutBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.workout.adapters.WorkoutAdapter

class TodayWorkoutActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var binding: ActivityTodayWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        handleBackBtn()
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout())
        binding.recyclerView.adapter = adapter


        startButton = findViewById(R.id.startButton)
    }

    private fun handleBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}