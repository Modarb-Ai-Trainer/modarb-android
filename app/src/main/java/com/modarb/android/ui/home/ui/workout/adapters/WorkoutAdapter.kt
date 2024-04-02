package com.modarb.android.ui.home.ui.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.workout.models.WorkoutModel

class WorkoutAdapter(private val itemList: List<WorkoutModel>) :
    RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var workoutImage: ImageView = itemView.findViewById(R.id.imageView6)
        var workoutTitle: TextView = itemView.findViewById(R.id.textView9)
        var workoutDescription: TextView = itemView.findViewById(R.id.textView11)
        var workoutButton: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.workoutImage.setImageResource(item.imageResId)
        holder.workoutTitle.text = item.title
        holder.workoutDescription.text = item.description
        holder.workoutButton.text = item.button
        holder.workoutButton.setOnClickListener {
            // Handle button click
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
