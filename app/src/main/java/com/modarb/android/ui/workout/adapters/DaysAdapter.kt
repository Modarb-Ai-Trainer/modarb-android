package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.models.Day


class DaysAdapter(private val itemList: List<Day>, private var isTheCurrentWeek: Boolean) :
    RecyclerView.Adapter<DaysAdapter.ItemViewHolder>() {
    private var isTheCurrentDayFound: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_days_layout, parent, false)
        return ItemViewHolder(view)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTitle: TextView = itemView.findViewById(R.id.dayText)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dayData = itemList[position]
        holder.dayTitle.text = "${position + 1}D"

        holder.itemView.background =
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_day_style)
        if (!dayData.is_done && isTheCurrentWeek) {
            // This is the current day
            if (!isTheCurrentDayFound) {
                holder.itemView.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_current_day)
            }
            isTheCurrentDayFound = true
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
