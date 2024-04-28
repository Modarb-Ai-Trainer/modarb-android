package com.modarb.android.ui.home.ui.plan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.modarb.android.R
import com.modarb.android.ui.workout.adapters.TrainingWeeksAdapter
import com.modarb.android.ui.workout.models.YourItem


class MyPlanFragment : Fragment() {

    private lateinit var adapter: TrainingWeeksAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    private val dataList = mutableListOf<YourItem>()
    private lateinit var addCustomWorkout: FloatingActionButton

    @SuppressLint("NotifyDataSetChanged")
    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_plan, container, false)


        val toggleGroup: MaterialButtonToggleGroup = view.findViewById(R.id.toggle_button_group)
        toggleGroup.check(R.id.myPlanBtn)

        initViewPager(view)
        handleAddCustomWorkout(view)

        return view
    }

    private fun handleAddCustomWorkout(view: View) {

        addCustomWorkout = view.findViewById(R.id.addCustomWorkOut)
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        toggleButtonGroup = view.findViewById(R.id.toggle_button_group)

        val adapter = ViewPagerAdapter()
        viewPager.adapter = adapter

        toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.myPlanBtn -> viewPager.currentItem = 0
                    R.id.customWorkOut -> viewPager.currentItem = 1
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.myPlanBtn
                        1 -> R.id.customWorkOut
                        else -> View.NO_ID
                    }
                )
                if (position == 0) {
                    addCustomWorkout.hide()
                } else {
                    addCustomWorkout.show()
                }
            }
        })
    }

    inner class ViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                is MyPlanViewHolder -> holder.bind()
                is CustomWorkoutViewHolder -> holder.bind()

            }
        }

        override fun getItemCount(): Int = 2

        override fun getItemViewType(position: Int): Int {
            return position
        }
    }

    inner class MyPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        fun bind() {
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
        fun bind() {
        }
    }
}
