package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
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
        handleBackButton()
        setRecycleData()
        setData()
    }

    private fun handleBackButton() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        var totalSets = 0
        val workoutData = WorkoutData.getTodayWorkout()

        for (i in workoutData!!.exercises) {
            totalSets += i.sets
        }
        binding.dayDetails.text =
            "Day ${workoutData.day_number} / ${WorkoutData.getWeekDaysCount()} - ${WorkoutData.getTodayWorkout()?.day_type}"
        binding.exerciseCount.text = "Exercises \n ${workoutData.total_number_exercises}"
        binding.setsCount.text = "Sets \n ${totalSets}"
        binding.expectedTime.text = "Duration \n ${workoutData.exercises.get(0).duration} min"
    }

    private fun setRecycleData() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout())
        binding.recyclerView.adapter = adapter
    }

}
