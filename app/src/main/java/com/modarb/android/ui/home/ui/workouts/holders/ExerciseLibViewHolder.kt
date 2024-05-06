package com.modarb.android.ui.home.ui.workouts.holders

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.ui.home.ui.workouts.adapters.ExerciseInfoAdapter
import com.modarb.android.ui.home.ui.workouts.models.Exercise


class ExerciseLibViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {

    private var btnTest: Button = view.findViewById(R.id.cardioBtn)
    private var bottomSheet = BottomSheetDialog(context)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseInfoAdapter
    private lateinit var exercises: ArrayList<Exercise>
    fun bind(context: Context) {
        initBottomSheet(context)
        btnTest.setOnClickListener {
            bottomSheet.show()
        }
    }

    private fun initBottomSheet(context: Context) {
        bottomSheet.setContentView(R.layout.exercise_all_workout_view)

        val closeBtn: ImageButton? = bottomSheet.findViewById(R.id.closeBtn)

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

        initRecycleView(bottomSheet, context)

    }

    private fun initRecycleView(view: BottomSheetDialog, context: Context) {

        recyclerView = view.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        exercises = ArrayList()
        exercises.add(
            Exercise(
                "Dumbbell Shoulder Press", R.mipmap.ic_launcher
            )
        )
        exercises.add(
            Exercise(
                "Dumbbell Shoulder Press", R.mipmap.ic_launcher
            )
        )
        exercises.add(
            Exercise(
                "Dumbbell Shoulder Press", R.mipmap.ic_launcher
            )
        )
        exercises.add(
            Exercise(
                "Dumbbell Shoulder Press", R.mipmap.ic_launcher
            )
        )

        adapter = ExerciseInfoAdapter(exercises)
        recyclerView.adapter = adapter
    }

}