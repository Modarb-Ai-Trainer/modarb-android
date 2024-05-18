package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.modarb.android.databinding.ActivityWeeklyWorkoutBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.domain.models.Day
import com.modarb.android.ui.workout.adapters.DaysTimeLineAdapter
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
        setTimeLineRecycler()
        handleOnStartButton()
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

        binding.weekTitle.text =
            "${WorkoutData.getCurrentWeek()!!.week_number}: ${WorkoutData.getCurrentWeek()!!.week_name}"
        binding.weekDesc.text = WorkoutData.getCurrentWeek()!!.week_description
        binding.dayDetails.text =
            "Day ${workoutData.day_number} / ${WorkoutData.getWeekDaysCount()} - ${WorkoutData.getTodayWorkout()?.day_type}"
        binding.exerciseCount.text = "Exercises \n ${workoutData.total_number_exercises}"
        binding.setsCount.text = "Sets \n ${totalSets}"
        binding.expectedTime.text = "Duration \n ${workoutData.exercises.get(0).duration} min"
    }

    private fun setTimeLineRecycler() {
        binding.timelineRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = DaysTimeLineAdapter(getTheDays())
        binding.timelineRecyclerView.adapter = adapter

    }

    private fun setRecycleData() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = WorkoutAdapter(WorkoutData.getTodayWorkout(), this)
        binding.recyclerView.adapter = adapter
    }

    private fun handleOnStartButton() {
        binding.startButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }

    private fun getTheDays(): ArrayList<Day> {
        val days: ArrayList<Day> = WorkoutData.getCurrentWeek()!!.days as ArrayList<Day>
        if (days[days.size - 1].day_number != 99) {
            val dummyDay = Day(
                day_number = 99,
                day_type = "Dummy Day",
                exercises = emptyList(),
                is_done = false,
                total_number_exercises = 0
            )

            days.add(dummyDay)
        }
        return days
    }

}
