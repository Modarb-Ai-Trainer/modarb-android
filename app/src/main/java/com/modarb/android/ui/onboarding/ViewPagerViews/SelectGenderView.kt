package com.modarb.android.ui.onboarding.ViewPagerViews

import android.util.Log
import android.view.View
import android.widget.DatePicker
import com.modarb.android.R
import com.modarb.android.ui.onboarding.models.UserRegisterData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SelectGenderView(view: View) {

    private val maleView = view.findViewById<View>(R.id.maleGenderView)
    private val femaleView = view.findViewById<View>(R.id.femaleGenderView)
    private val datePicker: DatePicker = view.findViewById(R.id.datePicker)

    init {
        setupClickListeners()
        setupNumberPicker()
    }

    private fun setupClickListeners() {
        maleView.setOnClickListener {
            UserRegisterData.registerRequest.gender = "male"
            updateBackgroundDrawable(maleView, R.drawable.circular_background_neon)
            updateBackgroundDrawable(femaleView, R.drawable.circular_background)
        }

        femaleView.setOnClickListener {
            UserRegisterData.registerRequest.gender = "female"
            updateBackgroundDrawable(femaleView, R.drawable.circular_background_neon)
            updateBackgroundDrawable(maleView, R.drawable.circular_background)
        }
    }

    private fun setupNumberPicker() {

        val calendar = Calendar.getInstance()

        // Set maximum date for the DatePicker
        datePicker.maxDate = System.currentTimeMillis()

        var formattedDate = formatDate(calendar.time)
        UserRegisterData.registerRequest.dob = formattedDate
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            formattedDate = formatDate(calendar.time)
            UserRegisterData.registerRequest.dob = formattedDate
            Log.d("birthdate", UserRegisterData.registerRequest.dob)
        }
        datePicker.setOnClickListener {
            UserRegisterData.registerRequest.dob = formattedDate
            Log.d("birthdate22", UserRegisterData.registerRequest.dob)
        }
    }

    private fun formatDate(date: Date): String {
        // Format the date into "YYYY/MM/DD" format
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun updateBackgroundDrawable(view: View, drawableId: Int) {
        view.setBackgroundResource(drawableId)
    }
}
