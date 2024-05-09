package com.modarb.android.ui.home.ui.plan.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R

class ExercisesAddAdapter(private val itemList: List<WorkoutModel>) :
    RecyclerView.Adapter<ExercisesAddAdapter.ViewHolder>() {

    private val selectedItems = mutableListOf<Int>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var overlayView: View = itemView.findViewById(R.id.overlay)
        private var checkMark: ImageView = itemView.findViewById(R.id.checkMark)

        fun bind(item: WorkoutModel) {


            val isSelected = selectedItems.contains(item.id)
            itemView.isActivated = isSelected
            overlayView.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
            checkMark.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE

            itemView.setOnClickListener {
                toggleSelection(item.id, adapterPosition)
                Log.e("selected", getSelectedItems().toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_selection_details, parent, false)
        return ViewHolder(view)
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
