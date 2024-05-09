package com.modarb.android.ui.workout.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.workout.adapters.WorkoutAdapter

class WeeklyWorkoutActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_workout)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.visibility = View.VISIBLE

        val itemList: ArrayList<WorkoutModel> = ArrayList()

        val workout1 =
            WorkoutModel(R.drawable.chest_press, "chest press", "4 sets x  12-15 reps", "chest", 1)
        val workout2 =
            WorkoutModel(R.drawable.deadlift, "Deadlift", "4 sets x  10-12 reps", "full body", 2)

        itemList.add(workout1)
        itemList.add(workout2)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = WorkoutAdapter(itemList)
        recyclerView.adapter = adapter


        val itemList2: ArrayList<YourItem2> = ArrayList()
        itemList2.add(YourItem2("D1"))
        itemList2.add(YourItem2("D2"))
        itemList2.add(YourItem2("D3"))
        itemList2.add(YourItem2("D4"))

    }
}
