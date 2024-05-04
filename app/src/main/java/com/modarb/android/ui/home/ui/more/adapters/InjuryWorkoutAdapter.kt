package com.modarb.android.ui.home.ui.more.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.models.InjuryWorkoutModel

class InjuryWorkoutAdapter(private val itemList: ArrayList<InjuryWorkoutModel>) :
    RecyclerView.Adapter<InjuryWorkoutAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var workoutImage: ImageView = itemView.findViewById(R.id.imageView6)
        var workoutDescription: TextView = itemView.findViewById(R.id.exerciseDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_injury_workout_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.workoutImage.setImageResource(item.imageResId)
        holder.workoutDescription.text = item.description
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
