package com.modarb.android.ui.onboarding.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.models.UserRegisterData
import com.modarb.android.ui.onboarding.utils.Data
import kotlin.math.abs

class SelectedItemsAdapter(
    private val type: String
) : RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder>() {
    var itemList = if (type == "preferred_equipment") {
        Data.equipmentList
    } else {
        Data.painPositionsList
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {

            checkBox.setOnClickListener {
                itemList[abs(adapterPosition + 1) - 1].isSelected =
                    !itemList[abs(adapterPosition + 1) - 1].isSelected
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
        saveData()
    }

    private fun saveData() {
        val dataToSave = mutableListOf<String>()

        for (it in itemList) {
            if (it.isSelected) {
                dataToSave.add(Data.getSelected(it.name))
            }
        }
        if (type == "preferred_equipment") {
            UserRegisterData.registerRequest.preferences.preferred_equipment = dataToSave
        } else {
            UserRegisterData.registerRequest.injuries = dataToSave
        }
        Log.d("SelectedItems", dataToSave.toString())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.checkBox.text = itemList[position].name
        holder.checkBox.isChecked = itemList[position].isSelected
        updateUI(holder.checkBox)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
