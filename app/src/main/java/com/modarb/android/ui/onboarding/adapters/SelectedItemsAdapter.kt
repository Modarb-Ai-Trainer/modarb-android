package com.modarb.android.ui.onboarding.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.models.ItemSelectionModel
import com.modarb.android.ui.onboarding.models.UserRegisterData
import com.modarb.android.ui.onboarding.utils.Data

class SelectedItemsAdapter(
    private val items: List<ItemSelectionModel>,
    private val listener: OnItemClickListener,
    private val type: String
) : RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder>() {
    val selected = mutableListOf<String>()

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
                Log.d("SelectedItems", selected.toString())
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
            selected.add(Data.getSelected(checkBox))
            checkBox.setBackgroundResource(R.drawable.neon_blue_shape)
        } else {
            selected.remove(Data.getSelected(checkBox))
            checkBox.setBackgroundResource(R.drawable.neon_blue_edges)
        }
        saveData()
    }

    private fun saveData() {
        // setting selected data to the pref
        if (type == "preferred_equipment") {
            UserRegisterData.registerRequest.preferences.preferred_equipment = selected
        } else {
            UserRegisterData.registerRequest.injuries = selected
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
