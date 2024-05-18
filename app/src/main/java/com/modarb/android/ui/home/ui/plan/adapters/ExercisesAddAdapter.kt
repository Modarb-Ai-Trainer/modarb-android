package com.modarb.android.ui.home.ui.plan.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemExerciseSelectionDetailsBinding
import com.modarb.android.ui.workout.domain.models.WorkoutModel

class ExercisesAddAdapter(private val itemList: List<WorkoutModel>) :
    RecyclerView.Adapter<ExercisesAddAdapter.ViewHolder>() {

    private val selectedItems = mutableListOf<Int>()

    inner class ViewHolder(private val binding: ItemExerciseSelectionDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutModel) {
            val isSelected = selectedItems.contains(item.id)
            binding.root.isActivated = isSelected
            binding.overlay.visibility = if (isSelected) ViewGroup.VISIBLE else ViewGroup.INVISIBLE
            binding.checkMark.visibility =
                if (isSelected) ViewGroup.VISIBLE else ViewGroup.INVISIBLE

            binding.root.setOnClickListener {
                toggleSelection(item.id, adapterPosition)
                Log.d("selected", getSelectedItems().toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseSelectionDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun toggleSelection(itemId: Int, position: Int) {
        if (selectedItems.contains(itemId)) {
            selectedItems.remove(itemId)
        } else {
            selectedItems.add(itemId)
        }
        notifyItemChanged(position)
    }

    fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }
}
