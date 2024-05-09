package com.modarb.android.ui.workout.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.modarb.android.databinding.ActivityTodayWorkoutBinding

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
//        val itemList: ArrayList<WorkoutModel> = ArrayList()
//
//        val workout1 =
//            WorkoutModel(R.drawable.chest_press, "chest press", "4 sets x  12-15 reps", "chest", 1)
//        val workout2 =
//            WorkoutModel(R.drawable.deadlift, "Deadlift", "4 sets x  10-12 reps", "full body", 2)
//
//        itemList.add(workout1)
//        itemList.add(workout2)
//
//        val adapter = WorkoutAdapter(itemList)
//        binding.recyclerView.adapter = adapter
//
//
//        startButton = findViewById(R.id.startButton)
    }

    private fun handleBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}