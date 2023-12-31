package com.modarb.android.ui.onboarding.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.models.ItemSelectionModel

class SelectedItemsAdapter(
    private val items: List<ItemSelectionModel>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {

            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    listener.onItemClick(adapterPosition + 1)
                } else {
                    listener.onItemClick(-(adapterPosition + 1))
                }
                updateUI(checkBox)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_checkbox, parent, false)
        return ViewHolder(view)
    }

    private fun updateUI(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            checkBox.setBackgroundResource(R.drawable.neon_blue_shape)
        } else {
            checkBox.setBackgroundResource(R.drawable.neon_blue_edges)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.checkBox.text = currentItem.name
        holder.checkBox.isChecked = currentItem.isSelected

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
