package com.modarb.android.ui.onboarding.helpers

import android.view.View
import android.widget.NumberPicker
import com.modarb.android.R

class SelectGenderView(view: View) {

    private val maleView = view.findViewById<View>(R.id.maleGenderView)
    private val femaleView = view.findViewById<View>(R.id.femaleGenderView)
    private val numberPicker: NumberPicker = view.findViewById(R.id.numberPicker)

    init {
        setupClickListeners()
        setupNumberPicker()
    }

    private fun setupClickListeners() {
        maleView.setOnClickListener {
            updateBackgroundDrawable(maleView, R.drawable.circular_background_neon)
            updateBackgroundDrawable(femaleView, R.drawable.circular_background)
        }

        femaleView.setOnClickListener {
            updateBackgroundDrawable(femaleView, R.drawable.circular_background_neon)
            updateBackgroundDrawable(maleView, R.drawable.circular_background)
        }
    }

    private fun setupNumberPicker() {
        numberPicker.minValue = 6
        numberPicker.maxValue = 100
    }

    private fun updateBackgroundDrawable(view: View, drawableId: Int) {
        view.setBackgroundResource(drawableId)
    }
}
