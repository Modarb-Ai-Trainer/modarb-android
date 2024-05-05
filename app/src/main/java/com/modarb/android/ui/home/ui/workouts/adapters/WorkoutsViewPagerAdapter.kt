package com.modarb.android.ui.home.ui.workouts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.workouts.holders.ExerciseLibViewHolder
import com.modarb.android.ui.home.ui.workouts.holders.WorkoutProgramsViewHolder

class WorkoutsViewPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> ExerciseLibViewHolder(
                inflater.inflate(
                    R.layout.exercise_library_view, parent, false
                ), context
            )

            1 -> WorkoutProgramsViewHolder(
                // TODO CREATE VIEW FOR THIS
                inflater.inflate(
                    R.layout.exercise_library_view, parent, false
                ), context
            )


            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExerciseLibViewHolder -> holder.bind(context)
            is WorkoutProgramsViewHolder -> holder.bind(context)

        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

