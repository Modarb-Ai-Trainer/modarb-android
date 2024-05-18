package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.modarb.android.R
import com.modarb.android.databinding.ItemTimelineBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.domain.models.Day
import com.modarb.android.ui.home.ui.plan.domain.models.Week
import com.modarb.android.ui.workout.activities.WeeklyWorkoutActivity

class TrainingWeeksAdapter(private val dataList: List<Week>, private val context: Context) :
    RecyclerView.Adapter<TrainingWeeksAdapter.TrainingWeeksViewHolder>() {

    inner class TrainingWeeksViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weekData: Week, isTheCurrentWeek: Boolean) {
            binding.apply {
                weekName.text = weekData.week_name
                binding.timeline.initLine(itemViewType)

                if (isTheCurrentWeek) {
                    WorkoutData.currentWeekPosition = adapterPosition
                    setMarker(R.drawable.ic_marker)
                    bindDaysAdapter(weekData.days, true)
                    weekDesc.text = weekData.week_description
                    itemView.setOnClickListener {
                        context.startActivity(Intent(context, WeeklyWorkoutActivity::class.java))
                    }
                } else {
                    setMarker(R.drawable.ic_marker_inactive)
                    bindDaysAdapter(weekData.days, false)
                }
            }
        }

        private fun bindDaysAdapter(days: List<Day>, isTheCurrentWeek: Boolean) {
            val adapter = DaysAdapter(days, isTheCurrentWeek)
            binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.adapter = adapter
        }

        private fun setMarker(drawableResId: Int) {
            binding.timeline.marker = ContextCompat.getDrawable(context, drawableResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingWeeksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
        return TrainingWeeksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingWeeksViewHolder, position: Int) {
        val weekData = dataList[position]
        val isTheCurrentWeek = !isTheCurrentWeekFound && !weekData.is_done

        holder.bind(weekData, isTheCurrentWeek)
        isTheCurrentWeekFound = isTheCurrentWeek
    }

    private var isTheCurrentWeekFound: Boolean = false

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}
