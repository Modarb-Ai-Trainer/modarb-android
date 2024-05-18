package com.modarb.android.ui.nutrition.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.nutrition.models.AvailablePlanModel

class AvailablePlanAdapter(private val dataList: List<AvailablePlanModel>, private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<AvailablePlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_available_plan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = dataList[position]
        holder.itemView.setBackgroundResource(plan.imageResId)
        holder.bind(plan)
        holder.itemView.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        private val textViewDesItem: TextView = itemView.findViewById(R.id.textViewDesItem)

        fun bind(plan: AvailablePlanModel) {
            textViewItem.text = plan.name
            textViewDesItem.text = plan.description
        }
    }
}

