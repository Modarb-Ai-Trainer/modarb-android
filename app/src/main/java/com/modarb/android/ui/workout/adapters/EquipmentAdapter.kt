package com.modarb.android.ui.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.domain.models.Equipment

class EquipmentAdapter(private val data: List<Equipment>) :
    RecyclerView.Adapter<EquipmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_equipment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position].name)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO ADD IMAGE
        private val exerciseName: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: String) {
            exerciseName.text = item
        }
    }
}
