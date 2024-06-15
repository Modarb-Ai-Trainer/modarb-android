package com.modarb.android.ui.home.ui.workouts.holders

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.WorkoutsProgramsViewBinding
import com.modarb.android.ui.home.ui.workouts.adapters.WorkoutProgramAdapter
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

class WorkoutProgramsViewHolder(private val binding: WorkoutsProgramsViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context, workoutProgramsResponse: WorkoutProgramsResponse?) {
        binding.recycleView.layoutManager = LinearLayoutManager(context)
        binding.recycleView.adapter = WorkoutProgramAdapter(context, workoutProgramsResponse!!.data)
    }
}
