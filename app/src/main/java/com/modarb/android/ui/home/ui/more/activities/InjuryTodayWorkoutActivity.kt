package com.modarb.android.ui.home.ui.more.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.adapters.InjuryWorkoutAdapter
import com.modarb.android.ui.home.ui.more.models.InjuryWorkoutModel

class InjuryTodayWorkoutActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var startButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injury_today_workout)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemList: ArrayList<InjuryWorkoutModel> = ArrayList()

        val workout1 = InjuryWorkoutModel(R.drawable.daily_recovery_session1,"4 sets x  12-15 reps", 1)
        val workout2 = InjuryWorkoutModel(R.drawable.daily_recovery_session2, "4 sets x  10-12 reps", 2)

        itemList.add(workout1)
        itemList.add(workout2)

        val adapter = InjuryWorkoutAdapter(itemList)

        recyclerView.adapter = adapter


        startButton = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
        }
    }
}