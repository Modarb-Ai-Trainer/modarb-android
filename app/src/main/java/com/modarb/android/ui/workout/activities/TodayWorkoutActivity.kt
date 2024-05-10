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


        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout(), this)
        binding.recyclerView.adapter = adapter
        setData()

        startButton = findViewById(R.id.startButton)
    }

    private fun setData() {
        var totalSets = 0
        val workoutData = WorkoutData.getTodayWorkout()

        for (i in workoutData!!.exercises) {
            totalSets += i.sets
        }
        binding.exerciseCount.text = "Exercises \n ${workoutData.total_number_exercises}"
        binding.setsCount.text = "Sets \n ${totalSets}"
        binding.timeCount.text = "Duration \n ${workoutData.exercises.get(0).duration} min"
    }

    private fun handleBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}