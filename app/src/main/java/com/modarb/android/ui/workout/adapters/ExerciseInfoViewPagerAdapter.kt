package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R

class ExerciseInfoViewPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> MusclesWorkedViewHolder(
                inflater.inflate(
                    R.layout.muscels_worked_view, parent, false
                )
            )

            1 -> InstructionsViewHolder(
                inflater.inflate(
                    R.layout.instructions_view, parent, false
                )
            )

            2 -> ExerciseInfoViewHolder(
                inflater.inflate(
                    R.layout.equipments_view, parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
//                is MusclesWorkedViewHolder -> holder.bind("My Plan Text")
//                is InstructionsViewHolder -> holder.bind("Custom Workout Text")
            is ExerciseInfoViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 3

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class MusclesWorkedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val textView: TextView = view.findViewById(R.id.textViewMyPlan)
//
//        fun bind(text: String) {
//            textView.text = text
//        }
}

class InstructionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val textView: TextView = view.findViewById(R.id.textViewCustomWorkout)
//
//        fun bind(text: String) {
//            textView.text = text
//        }
}

class ExerciseInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

    fun bind(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context)

        val data = listOf("Item 1", "Item 2", "Item 3")
        val adapter = EquipmentAdapter(data)
        recyclerView.adapter = adapter
    }
}