package com.modarb.android.ui.home.ui.plan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.adapters.ExercisesAddAdapter
import com.modarb.android.ui.home.ui.plan.adapters.ViewPagerAdapter
import com.modarb.android.ui.workout.models.WorkoutModel


class MyPlanFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    private lateinit var addCustomWorkout: FloatingActionButton
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var selectExerciseBottomSheet: BottomSheetDialog

    @SuppressLint("NotifyDataSetChanged")
    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_plan, container, false)


        val toggleGroup: MaterialButtonToggleGroup = view.findViewById(R.id.toggle_button_group)
        toggleGroup.check(R.id.myPlanBtn)

        initViewPager(view)
        initBottomSheet()
        handleAddCustomWorkout(view)
        initSelectBottomSheet()
        return view
    }

    private fun handleAddCustomWorkout(view: View) {

        addCustomWorkout = view.findViewById(R.id.addCustomWorkOut)


        addCustomWorkout.setOnClickListener {
            bottomSheet.show()
        }
    }

    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.add_excersice_view)

        val closeBtn: ImageButton? = bottomSheet.findViewById(R.id.closeBtn)
        val addExercise: View? = bottomSheet.findViewById(R.id.addExerciseView)

        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        closeBtn?.setOnClickListener {
            bottomSheet.hide()
        }


        addExercise?.setOnClickListener {
            selectExerciseBottomSheet.show()
        }
    }


    private fun initSelectBottomSheet() {
        // TODO refactor this shit
        selectExerciseBottomSheet = BottomSheetDialog(requireContext())
        selectExerciseBottomSheet.setContentView(R.layout.select_exersice_view)

        val closeBtn: ImageButton? = selectExerciseBottomSheet.findViewById(R.id.closeBtn)
        val recyclerView: RecyclerView? = selectExerciseBottomSheet.findViewById(R.id.recyclerView)

        val spinner: Spinner? = selectExerciseBottomSheet.findViewById(R.id.typeSpinner)

        selectExerciseBottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        selectExerciseBottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        closeBtn?.setOnClickListener {
            selectExerciseBottomSheet.hide()
        }

        recyclerView!!.layoutManager = LinearLayoutManager(context)

        val data = mutableListOf<WorkoutModel>()

        for (i in 0..3) {
            data.add(WorkoutModel(1, "test", "test", "test", i + 1))
        }

        val adapter = ExercisesAddAdapter(data)
        recyclerView.adapter = adapter


        // spinner
        val items = arrayOf("All", "Body", "Chest")

        val spinnerAdapter = ArrayAdapter(
            requireContext(), R.layout.custom_spinner_item, items
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setSelection(0)

        spinner.adapter = spinnerAdapter
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        toggleButtonGroup = view.findViewById(R.id.toggle_button_group)

        val adapter = ViewPagerAdapter(requireContext())
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
                    bottomSheet.hide()
                } else {
                    addCustomWorkout.show()
                }
            }
        })
    }


}
