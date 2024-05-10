package com.modarb.android.ui.home.ui.plan.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.logic.PlanViewModel
import com.modarb.android.ui.home.ui.plan.models.Data
import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.workout.activities.TodayWorkoutActivity
import com.modarb.android.ui.workout.adapters.TrainingWeeksAdapter

class MyPlanViewPagerAdapter(
    private val context: Context, private var viewModel: PlanViewModel
) :


    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> MyPlanViewHolder(
                inflater.inflate(
                    R.layout.my_plan_view, parent, false
                )
            )

            1 -> CustomWorkoutViewHolder(
                inflater.inflate(
                    R.layout.custom_workout_view, parent, false
                )
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

    inner class MyPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        private val fitLevel: TextView = view.findViewById(R.id.fitLevel)
        private val fitGoal: TextView = view.findViewById(R.id.fitGoal)
        private val planDesc: TextView = view.findViewById(R.id.planDesc)
        private val workPlace: TextView = view.findViewById(R.id.workPlace)
        private val workEquip: TextView = view.findViewById(R.id.workEquip)
        private val workTime: TextView = view.findViewById(R.id.workTime)
        private val workoutTotalDays: TextView = view.findViewById(R.id.workDays)
        private val todayWorkoutTime: TextView = view.findViewById(R.id.todayWorkoutTime)
        private val todayWorkoutName: TextView = view.findViewById(R.id.todayWorkoutNameTxt)
        private val exerciseCount: TextView = view.findViewById(R.id.exerciseCount)
        private val startWorkout: Button = view.findViewById(R.id.startWorkoutBtn)
        private lateinit var adapter: TrainingWeeksAdapter


        private fun setupRecyclerView(context: Context) {
            recyclerView.layoutManager = LinearLayoutManager(context)


            if (!::adapter.isInitialized) {
                adapter = TrainingWeeksAdapter(
                    viewModel.planResponse.value?.body()?.data!!.weeks, context
                )
                recyclerView.adapter = adapter
            }
        }

        private fun loadDataIntoViews(context: Context) {
            viewModel.planResponse.value?.body()?.data?.let { data ->
                updateTextViews(data, context)
            } ?: run {
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        @SuppressLint("SetTextI18n")
        private fun updateTextViews(data: Data, context: Context) {
            todayWorkoutName.text = getTodayWorkout()?.day_type
            planDesc.text = data.workout.description
            fitGoal.text = data.workout.fitness_goal
            fitLevel.text = data.workout.fitness_level
            workPlace.text = data.workout.place.joinToString(separator = ", ")
            workTime.text = "${data.workout.min_per_day} ${context.getString(R.string.min)} / day"
            todayWorkoutTime.text = "${data.workout.min_per_day} ${context.getString(R.string.min)}"
            exerciseCount.text =
                getTodayWorkout()?.total_number_exercises.toString() + " " + context.getString(R.string.exercise)
            workEquip.text = data.workout.type
            workoutTotalDays.text =
                data.weeks.size.toString() + " " + context.getString(R.string.days)
        }

        /*
    Function that will get today workout by finding the first is done = false week
    then find the first undone exercise day

     */
        private fun getTodayWorkout(): Day? {
            val weekList = viewModel.planResponse.value!!.body()!!.data.weeks

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
            startWorkout.setOnClickListener {
                context.startActivity(Intent(context, TodayWorkoutActivity::class.java))

            }
        }

        fun bind(context: Context) {
            setupRecyclerView(context)
            loadDataIntoViews(context)
            startWorkout()
        }


    }

    inner class CustomWorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recyclerView: RecyclerView = view.findViewById(R.id.recycleView)

        private val data = mutableListOf<String>()

        fun bind(context: Context) {
            recyclerView.layoutManager = LinearLayoutManager(context)


            for (i in 0..3) {
                data.add(
                    "test"
                )
            }

            val adapter = CustomWorkoutTemplateAdapter(context, data)
            recyclerView.adapter = adapter


        }
    }

}

