package com.modarb.android.ui.home.ui.workout.activites

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.workout.adapters.WorkoutAdapter
import com.modarb.android.ui.home.ui.workout.models.WorkoutModel

class WorkoutActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var startButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemList: ArrayList<WorkoutModel> = ArrayList()

        val workout1 = WorkoutModel(R.drawable.chest_press, "chest press", "4 sets x  12-15 reps", "chest")
        val workout2 = WorkoutModel(R.drawable.deadlift, "Deadlift", "4 sets x  10-12 reps", "full body")
        // Add more items as needed


        itemList.add(workout1)
        itemList.add(workout2)

        val adapter = WorkoutAdapter(itemList)
        recyclerView.adapter = adapter


        startButton = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
        }
    }
}