package com.modarb.android.ui.workout.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButtonToggleGroup
import com.modarb.android.R
import com.modarb.android.ui.workout.adapters.EquipmentAdapter

class ExerciseInfoActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager2
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_info)



        viewPager = findViewById(R.id.viewPager)
        toggleButtonGroup = findViewById(R.id.toggle_button_group)

        val adapter = ViewPagerAdapter()
        viewPager.adapter = adapter

        toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.targetMuscleBtn -> viewPager.currentItem = 0
                    R.id.instructionsMuscleBtn -> viewPager.currentItem = 1
                    R.id.equipmentBtn -> viewPager.currentItem = 2
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.targetMuscleBtn
                        1 -> R.id.instructionsMuscleBtn
                        2 -> R.id.equipmentBtn
                        else -> View.NO_ID
                    }
                )
            }
        })
    }


    inner class ViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                0 -> MusclesWorkedViewHolder(
                    inflater.inflate(
                        R.layout.muscels_worked_view, parent, false
                    )
                )

                1 -> InstructionsViewHolder(
                    inflater.inflate(
                        R.layout.instructions_view, parent, false
                    )
                )

                2 -> ExerciseInfoViewHolder(
                    inflater.inflate(
                        R.layout.equipments_view, parent, false
                    )
                )

                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
//                is MusclesWorkedViewHolder -> holder.bind("My Plan Text")
//                is InstructionsViewHolder -> holder.bind("Custom Workout Text")
                is ExerciseInfoViewHolder -> holder.bind("Test")
            }
        }

        override fun getItemCount(): Int = 3

        override fun getItemViewType(position: Int): Int {
            return position
        }
    }

    inner class MusclesWorkedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val textView: TextView = view.findViewById(R.id.textViewMyPlan)
//
//        fun bind(text: String) {
//            textView.text = text
//        }
    }

    inner class InstructionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val textView: TextView = view.findViewById(R.id.textViewCustomWorkout)
//
//        fun bind(text: String) {
//            textView.text = text
//        }
    }

    inner class ExerciseInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        fun bind(text: String) {
            recyclerView.layoutManager = LinearLayoutManager(this@ExerciseInfoActivity)

            val data = listOf("Item 1", "Item 2", "Item 3")
            val adapter = EquipmentAdapter(data)
            recyclerView.adapter = adapter
        }
    }

}