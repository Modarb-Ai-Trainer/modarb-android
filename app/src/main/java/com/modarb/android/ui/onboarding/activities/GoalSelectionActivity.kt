package com.modarb.android.ui.onboarding.activities

import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.modarb.android.R

class GoalSelectionActivity : AppCompatActivity() {

    private var selectedTextView: TextView? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_selection)

        setupTextView(findViewById(R.id.textView2), R.string.lose_weight, R.string.want_to_burn_fats_and_lose_weight)

        setupTextView(findViewById(R.id.textView3), R.string.gain_muscle, R.string.want_to_gain_weight_and_or_build_muscle)

        setupTextView(findViewById(R.id.textView4), R.string.get_fitter, R.string.want_to_enhance_my_overall_physical_fitness)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupTextView(textView: TextView, primaryTextResId: Int, additionalTextResId: Int) {

        applyFormatting(textView, primaryTextResId, additionalTextResId)

        textView.setOnClickListener {
            selectedTextView?.setBackgroundResource(R.drawable.neon_blue_edges)

            textView.setBackgroundResource(R.drawable.neon_blue_shape)

            selectedTextView = textView

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun applyFormatting(textView: TextView, primaryTextResId: Int, additionalTextResId: Int) {
        val primaryText = getString(primaryTextResId)
        val additionalText = getString(additionalTextResId)

        val combinedText = SpannableString("$primaryText\n$additionalText")

        combinedText.setSpan(ForegroundColorSpan(getColor(R.color.white_violet)), 0, primaryText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        combinedText.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, primaryText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        combinedText.setSpan(AbsoluteSizeSpan(24, true), 0, primaryText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val additionalTextStart = primaryText.length + 1
        val additionalTextEnd = additionalTextStart + additionalText.length
        combinedText.setSpan(ForegroundColorSpan(getColor(R.color.platinum)), additionalTextStart, additionalTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        combinedText.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), additionalTextStart, additionalTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        combinedText.setSpan(AbsoluteSizeSpan(16, true), additionalTextStart, additionalTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = combinedText

        val customFont = ResourcesCompat.getFont(this, R.font.montserrat)
        textView.typeface = customFont
    }
}
