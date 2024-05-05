package com.modarb.android.ui.home.ui.workouts.holders

import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R

class ExerciseLibViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {

    var btnTest = view.findViewById<Button>(R.id.cardioBtn)
    var bottomSheet = BottomSheetDialog(context)


    fun bind() {
        initBottomSheet()
        btnTest.setOnClickListener {
            Log.e("Test", "test")
            bottomSheet.show()
        }
    }

    private fun initBottomSheet() {
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

    }

}