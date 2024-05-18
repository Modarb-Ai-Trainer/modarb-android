package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.domain.models.Exercise

class ExerciseInfoViewPagerAdapter(private val context: Context, selectedExercise: Exercise) :
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
            is MusclesWorkedViewHolder -> holder.bind()
            is InstructionsViewHolder -> holder.bind()
            is ExerciseInfoViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 3

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class MusclesWorkedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //TODO add the image view
    private val primaryMuscles: TextView = view.findViewById(R.id.primary)
    private val secondaryMuscles: TextView = view.findViewById(R.id.secondary)

    fun bind() {
        primaryMuscles.text = WorkoutData.selectedExercise.targetMuscles.primary.name
        secondaryMuscles.text = WorkoutData.selectedExercise.targetMuscles.secondary.name
    }
}

class InstructionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val instructions: TextView = view.findViewById(R.id.instructions)
    private val benefits: TextView = view.findViewById(R.id.benefits)

    fun bind() {
        instructions.text = WorkoutData.selectedExercise.instructions
        benefits.text = WorkoutData.selectedExercise.benefits
    }
}

class ExerciseInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    //TODO add the image view

    fun bind(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = EquipmentAdapter(WorkoutData.selectedExercise.equipments)
        recyclerView.adapter = adapter
    }
}