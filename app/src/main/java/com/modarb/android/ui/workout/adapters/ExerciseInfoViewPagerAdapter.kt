package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.EquipmentsViewBinding
import com.modarb.android.databinding.InstructionsViewBinding
import com.modarb.android.databinding.MuscelsWorkedViewBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.plan.domain.models.Exercise

class ExerciseInfoViewPagerAdapter(
    private val context: Context, private val selectedExercise: Exercise
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> MusclesWorkedViewHolder(
                MuscelsWorkedViewBinding.inflate(inflater, parent, false)
            )

            1 -> InstructionsViewHolder(
                InstructionsViewBinding.inflate(inflater, parent, false)
            )

            2 -> ExerciseInfoViewHolder(
                EquipmentsViewBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MusclesWorkedViewHolder -> holder.bind(context, selectedExercise)
            is InstructionsViewHolder -> holder.bind(selectedExercise)
            is ExerciseInfoViewHolder -> holder.bind(context, selectedExercise)
        }
    }

    override fun getItemCount(): Int = 3

    override fun getItemViewType(position: Int): Int = position
}

class MusclesWorkedViewHolder(private val binding: MuscelsWorkedViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context, selectedExercise: Exercise) {
        binding.primary.text = selectedExercise.targetMuscles.primary.name
        binding.secondary.text = selectedExercise.targetMuscles.secondary.name
        ViewUtils.loadImage(
            context, selectedExercise.targetMuscles.primary.image, binding.primaryImageView
        )
        ViewUtils.loadImage(
            context, selectedExercise.targetMuscles.secondary.image, binding.secondyImageView
        )

    }
}

class InstructionsViewHolder(private val binding: InstructionsViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(selectedExercise: Exercise) {
        binding.instructions.text = selectedExercise.instructions
        binding.benefits.text = selectedExercise.benefits
    }
}

class ExerciseInfoViewHolder(private val binding: EquipmentsViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context, selectedExercise: Exercise) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = EquipmentAdapter(context, selectedExercise.equipments)
        binding.recyclerView.adapter = adapter
    }
}
