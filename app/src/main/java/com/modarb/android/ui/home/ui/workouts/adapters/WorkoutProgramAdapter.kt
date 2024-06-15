package com.modarb.android.ui.home.ui.workouts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemWorkoutProgramBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.workouts.OnWorkoutItemClickListener
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.Data

class WorkoutProgramAdapter(
    private val context: Context,
    private val workoutList: List<Data>,
    private val listener: OnWorkoutItemClickListener
) : RecyclerView.Adapter<WorkoutProgramAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(private val binding: ItemWorkoutProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: Data) {
            //TODO Uncomment this
            ViewUtils.loadImage(context, program.image, binding.workoutImage)
            binding.workoutName.text = program.name
            binding.workoutView.setOnClickListener {
                listener.onWorkoutItemClick(program)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding =
            ItemWorkoutProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workoutList[position])
    }

    override fun getItemCount(): Int = workoutList.size
}
