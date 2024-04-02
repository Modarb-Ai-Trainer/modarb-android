package com.modarb.android.ui.home.ui.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.workout.models.YourItem2

class TimelineWeeklyWorkoutAdapter (private val dataList: List<YourItem2>) : RecyclerView.Adapter<TimelineWeeklyWorkoutAdapter.YourViewHolder>() {

    inner class YourViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private val timelineView: TimelineView = itemView.findViewById(R.id.timeline)
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            timelineView.initLine(viewType)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline2, parent, false)
        return YourViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.textView.text = currentItem.textView
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}