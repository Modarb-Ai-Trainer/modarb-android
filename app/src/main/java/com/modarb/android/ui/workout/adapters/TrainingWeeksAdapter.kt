package com.modarb.android.ui.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.models.Week

class TrainingWeeksAdapter(private val dataList: List<Week>) :
    RecyclerView.Adapter<TrainingWeeksAdapter.TrainingWeeksAdapter>() {
    private var isTheCurrentWeekFound: Boolean = false

    inner class TrainingWeeksAdapter(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        val timelineView: TimelineView = itemView.findViewById(R.id.timeline)
        val weekName: TextView = itemView.findViewById(R.id.weekName)
        val weekDesc: TextView = itemView.findViewById(R.id.weekDesc)

        init {
            timelineView.initLine(viewType)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingWeeksAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)
        return TrainingWeeksAdapter(view, viewType)
    }

    override fun onBindViewHolder(holder: TrainingWeeksAdapter, position: Int) {
        val weekData = dataList[position]

        holder.weekName.text = weekData.week_name
        setMarker(holder, R.drawable.ic_marker_inactive)

        if (!weekData.is_done) {
            // This is the current week
            if (!isTheCurrentWeekFound) {
                setMarker(holder, R.drawable.ic_marker)
                holder.weekDesc.text = weekData.week_description
            }
            isTheCurrentWeekFound = true
        }
    }

    private fun setMarker(holder: TrainingWeeksAdapter, drawableResId: Int) {
        holder.timelineView.marker = getDrawable(holder.itemView.context, drawableResId)
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}
