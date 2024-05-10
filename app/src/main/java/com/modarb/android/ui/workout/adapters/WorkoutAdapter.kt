package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.workout.activities.ExerciseInfoActivity

class WorkoutAdapter(private val data: Day?, private var context: Context) :
    RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var workoutImage: ImageView = itemView.findViewById(R.id.imageView6)
        var workoutTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        var workoutDescription: TextView = itemView.findViewById(R.id.exerciseDesc)
        var workoutButton: Button = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.exercises?.get(position)

        // holder.workoutImage.setImageResource(item.imageResId)
        holder.workoutTitle.text = item!!.name
        holder.workoutDescription.text = "${item.sets} sets x ${item.reps} reps"
        holder.workoutButton.text = item.category

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ExerciseInfoActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return data?.exercises?.size!!
    }
}
