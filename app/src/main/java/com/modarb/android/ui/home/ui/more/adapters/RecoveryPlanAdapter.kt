package com.modarb.android.ui.home.ui.more.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R

class RecoveryPlanAdapter(private val dataList: List<String>, private val imageResources: List<Int>, private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecoveryPlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recovery_plan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResource = imageResources[position % imageResources.size]
        holder.itemView.setBackgroundResource(imageResource)
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        fun bind(data: String) {
            textViewItem.text = data
        }
    }
}
