package com.modarb.android.ui.home.ui.plan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.workout.adapters.TrainingWeeksAdapter
import com.modarb.android.ui.workout.models.YourItem

class MyPlanViewPagerAdapter(private val context: Context) :
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
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        private val dataList = mutableListOf<YourItem>()
        private lateinit var adapter: TrainingWeeksAdapter

        fun bind(context: Context) {
            recyclerView.layoutManager = LinearLayoutManager(context)



            dataList.add(
                YourItem(
                    "Week 1 : Foundation",
                    "Start easy in the first week to let your body get used to the workout. It sets the baseline for your progress in the weeks ahead."
                )
            )
            dataList.add(
                YourItem(
                    "Week 2 : Foundation",
                    "Start easy in the first week to let your body get used to the workout."
                )
            )

            adapter = TrainingWeeksAdapter(dataList)
            recyclerView.adapter = adapter
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

