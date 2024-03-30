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
    private lateinit var adapter: TrainingWeeksAdapter // Replace YourAdapter with your actual adapter class
    private val dataList =
        mutableListOf<YourItem>() // Replace YourData with your actual data model class

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize adapter
        adapter =
            TrainingWeeksAdapter(dataList) // Replace YourAdapter with your actual adapter class
        recyclerView.adapter = adapter

        // Add sample data (Replace this with your actual data source)
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
        // Add more data as needed

        // Notify adapter about the data change
        adapter.notifyDataSetChanged()

        return view
    }
}
