package com.modarb.android.ui.onboarding.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R

class GenderSelectionActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_selection)

        val container1 = findViewById<View>(R.id.circleBackground1)
        val container2 = findViewById<View>(R.id.circleBackground2)

        // Set OnClickListener for container1
        container1.setOnClickListener {
            changeBackgroundDrawable(container1, R.drawable.circular_background_neon)
            changeBackgroundDrawable(container2, R.drawable.circular_background)
        }

        // Set OnClickListener for container2
        container2.setOnClickListener {
            changeBackgroundDrawable(container2, R.drawable.circular_background_neon)
            changeBackgroundDrawable(container1, R.drawable.circular_background)
        }

        val numberPicker: NumberPicker = findViewById(R.id.numberPicker)

        numberPicker.minValue = 1
        numberPicker.maxValue = 100

        val numberPickerTextColor = getColor(R.color.white_smoke) // Replace with your desired red color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextColor(numberPickerTextColor)
        }

        // Set the number text color to red
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            val numberPickerTextViewId = resources.getIdentifier("android:id/number picker_input", null, null)
            val numberPickerTextView = numberPicker.findViewById<TextView>(numberPickerTextViewId)
            numberPickerTextView?.setTextColor(numberPickerTextColor)
        }
    }

    private fun changeBackgroundDrawable(view: View, drawableId: Int) {
        view.setBackgroundResource(drawableId)
    }
}
