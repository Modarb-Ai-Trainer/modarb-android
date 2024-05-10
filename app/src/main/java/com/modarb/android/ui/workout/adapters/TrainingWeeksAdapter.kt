package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.modarb.android.R
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.home.ui.plan.models.Week
import com.modarb.android.ui.workout.activities.WeeklyWorkoutActivity


class TrainingWeeksAdapter(private val dataList: List<Week>, private var context: Context) :
    RecyclerView.Adapter<TrainingWeeksAdapter.TrainingWeeksAdapter>() {
    private var isTheCurrentWeekFound: Boolean = false

    inner class TrainingWeeksAdapter(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        val timelineView: TimelineView = itemView.findViewById(R.id.timeline)
        val weekName: TextView = itemView.findViewById(R.id.weekName)
        val weekDesc: TextView = itemView.findViewById(R.id.weekDesc)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

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
        bindDaysAdapter(holder, weekData.days, false)

        if (!weekData.is_done) {
            // This is the current week
            if (!isTheCurrentWeekFound) {
                WorkoutData.currentWeekPosition = position
                setMarker(holder, R.drawable.ic_marker)
                bindDaysAdapter(holder, weekData.days, true)
                holder.weekDesc.text = weekData.week_description
                holder.itemView.setOnClickListener {
                    context.startActivity(Intent(context, WeeklyWorkoutActivity::class.java))
                }
            }
            isTheCurrentWeekFound = true
        }
    }

    private fun bindDaysAdapter(
        holder: TrainingWeeksAdapter,
        days: List<Day>,
        isTheCurrentWeek: Boolean
    ) {
        val adapter = DaysAdapter(days, isTheCurrentWeek)
        holder.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = adapter
    }

    private fun setMarker(holder: TrainingWeeksAdapter, drawableResId: Int) {
        holder.timelineView.marker = getDrawable(holder.itemView.context, drawableResId)
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}
