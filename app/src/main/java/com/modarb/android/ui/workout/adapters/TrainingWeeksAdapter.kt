package com.modarb.android.ui.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.models.Week

class TrainingWeeksAdapter(private val dataList: List<Week>) :
    RecyclerView.Adapter<TrainingWeeksAdapter.YourViewHolder>() {

    inner class YourViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        val timelineView: TimelineView = itemView.findViewById(R.id.timeline)
        val weekName: TextView = itemView.findViewById(R.id.weekName)
        val weekDesc: TextView = itemView.findViewById(R.id.weekDesc)

        init {
            timelineView.initLine(viewType)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)
        return YourViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        val weekData = dataList[position]
        holder.weekName.text = weekData.week_name
        holder.weekDesc.text = weekData.week_description
//        if (!weekData.is_done) holder.timelineView.marker = TimelineView.lineMarker()
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}
