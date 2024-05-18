package com.modarb.android.ui.home.ui.plan.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.CustomWorkoutViewBinding
import com.modarb.android.databinding.MyPlanViewBinding
import com.modarb.android.ui.home.ui.plan.domain.models.Data
import com.modarb.android.ui.home.ui.plan.domain.models.Day
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.workout.activities.TodayWorkoutActivity
import com.modarb.android.ui.workout.adapters.TrainingWeeksAdapter

class MyPlanViewPagerAdapter(
    private val context: Context, private var planResponse: PlanPageResponse
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> MyPlanViewHolder(
                MyPlanViewBinding.inflate(inflater, parent, false)
            )

            1 -> CustomWorkoutViewHolder(
                CustomWorkoutViewBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPlanViewHolder -> holder.bind(context)
            is CustomWorkoutViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyPlanViewHolder(private val binding: MyPlanViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var adapter: TrainingWeeksAdapter

        private fun setupRecyclerView(context: Context) {
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            if (!::adapter.isInitialized) {
                adapter = TrainingWeeksAdapter(
                    planResponse.data.weeks, context
                )
                binding.recyclerView.adapter = adapter
            }
        }

        private fun loadDataIntoViews(context: Context) {
            updateTextViews(planResponse.data, context)
        }

        @SuppressLint("SetTextI18n")
        private fun updateTextViews(data: Data, context: Context) {
            binding.todayWorkoutNameTxt.text = getTodayWorkout()?.day_type
            binding.planDesc.text = data.workout.description
            binding.fitGoal.text = data.workout.fitness_goal
            binding.fitLevel.text = data.workout.fitness_level
            binding.workPlace.text = data.workout.place.joinToString(separator = ", ")
            binding.workTime.text =
                "${data.workout.min_per_day} ${context.getString(R.string.min)} / day"
            binding.todayWorkoutTime.text =
                "${data.workout.min_per_day} ${context.getString(R.string.min)}"
            binding.exerciseCount.text =
                getTodayWorkout()?.total_number_exercises.toString() + " " + context.getString(R.string.exercise)
            binding.workEquip.text = data.workout.type
            binding.workDays.text =
                data.weeks.size.toString() + " " + context.getString(R.string.days)
        }

        private fun getTodayWorkout(): Day? {
            val weekList = planResponse.data.weeks
            for (week in weekList) {
                if (!week.is_done) {
                    for (day in week.days) {
                        if (!day.is_done) {
                            return day
                        }
                    }
                    break
                }
            }
            return null
        }

        private fun startWorkout() {
            binding.startWorkoutBtn.setOnClickListener {
                context.startActivity(Intent(context, TodayWorkoutActivity::class.java))
            }
        }

        fun bind(context: Context) {
            setupRecyclerView(context)
            loadDataIntoViews(context)
            startWorkout()
        }
    }

    inner class CustomWorkoutViewHolder(private val binding: CustomWorkoutViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val data = mutableListOf<String>()

        fun bind(context: Context) {
            binding.recycleView.layoutManager = LinearLayoutManager(context)
            for (i in 0..3) {
                data.add("test")
            }
            val adapter = CustomWorkoutTemplateAdapter(context, data)
            binding.recycleView.adapter = adapter
        }
    }
}
