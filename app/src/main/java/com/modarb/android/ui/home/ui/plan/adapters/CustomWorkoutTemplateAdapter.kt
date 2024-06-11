package com.modarb.android.ui.home.ui.plan.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemCustomworkoutTemplateBinding
import com.modarb.android.network.NetworkHelper
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.Data

class CustomWorkoutTemplateAdapter(
    private val context: Context, private val data: List<Data>
) : RecyclerView.Adapter<CustomWorkoutTemplateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCustomworkoutTemplateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemCustomworkoutTemplateBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.workoutName.text = item.name
        holder.binding.workoutDate.text = NetworkHelper.getDate(item.creationDate)
        holder.binding.timeTxt.text = WorkoutData.getTotalExerciseTime(item.exercises) + " min"

        var exerciseName = ""
        var exerciseDetails = ""
        var totalReps = 0
        var totalSets = 0


        for (exercise in item.exercises) {
            exerciseName += "- ${exercise.name} \n \n"
            totalReps += exercise.reps
            totalSets += exercise.sets
        }

        for (exercise in item.exercises) {
            exerciseDetails += if (exercise.duration > 0) {
                "${(exercise.duration)} sec"
            } else {
                "${exercise.reps} x ${exercise.sets} sets"
            }
            exerciseDetails += "\n \n"
        }

        exerciseDetails = exerciseDetails.trimEnd()
        exerciseName = exerciseName.trimEnd()

        holder.binding.exerciseName.text = exerciseName
        holder.binding.exerciseDetails.text = exerciseDetails
        holder.binding.repCount.text = (totalReps * totalSets).toString() + " reps"

    }

    override fun getItemCount(): Int {
        return data.size
    }
}
