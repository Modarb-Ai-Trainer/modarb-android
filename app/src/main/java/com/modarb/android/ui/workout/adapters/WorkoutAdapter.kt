package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemWorkoutDetailsBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.workout.activities.ExerciseInfoActivity

class WorkoutAdapter(private val data: Day?, private var context: Context) :
    RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemWorkoutDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWorkoutDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.exercises?.get(position)

        holder.binding.exerciseTitle.text = item!!.name
        holder.binding.exerciseDesc.text = "${item.sets} sets x ${item.reps} reps"
        holder.binding.button.text = item.category

        holder.itemView.setOnClickListener {
            WorkoutData.selectedExercise = item
            WorkoutData.selectedExerciseNumber = position + 1
            context.startActivity(Intent(context, ExerciseInfoActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return data?.exercises?.size ?: 0
    }
}
