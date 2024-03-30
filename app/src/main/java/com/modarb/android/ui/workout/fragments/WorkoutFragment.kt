package com.modarb.android.ui.workout.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.workout.adapters.TrainingWeeksAdapter
import com.modarb.android.ui.workout.models.YourItem

class WorkoutFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingWeeksAdapter
    private val dataList =
        mutableListOf<YourItem>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_workout, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter =
            TrainingWeeksAdapter(dataList)
        recyclerView.adapter = adapter

        dataList.add(
            YourItem(
                "Week 1 : Foundation",
                "Start easy in the first week to let your body get used to the workout. It sets the baseline for your progress in the weeks ahead."
            )
        )
        dataList.add(
            YourItem(
                "Week 2 : Foundation",
                "Start easy in the first week to let your body get used to the workout."
            )
        )

        adapter.notifyDataSetChanged()

        return view
    }
}
