package com.modarb.android.ui.home.ui.plan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.workout.models.WorkoutModel

class ExercisesAdapter(private val itemList: List<WorkoutModel>) :
    RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var workoutImage: ImageView = itemView.findViewById(R.id.imageView6)
        var workoutTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        var workoutDescription: TextView = itemView.findViewById(R.id.exerciseDesc)
        var workoutButton: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_selection_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.workoutImage.setImageResource(item.imageResId)
        holder.workoutTitle.text = item.title
        holder.workoutDescription.text = item.description
        holder.workoutButton.text = item.button

        holder.workoutButton.setOnClickListener {

            // TODO handle add and remove of an exercise
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
