package com.modarb.android.ui.onboarding.ViewPagerViews

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import com.modarb.android.R
import com.modarb.android.utils.RulerView

class SelectTargetWeightView(private val view: View, private val context: Context) {

    private var heightValue: Float = 170.0f
    private var weightValue: Float = 78.0f
    private var targetWeightValue: Float = 78.0f

    init {
        initRuler(R.id.heightPicker, R.id.heightTxt) { heightValue = it + 100 }
        initRuler(R.id.weightPicker, R.id.weightTxt) { weightValue = it }
        initRuler(R.id.targetWeightPicker, R.id.targetTxt) { targetWeightValue = it }
    }

    @SuppressLint("SetTextI18n")
    private fun initRuler(rulerId: Int, textId: Int, valueCallback: (Float) -> Unit) {
        val ruler: RulerView = view.findViewById(rulerId)
        val textView: TextView = view.findViewById(textId)

        textView.text = getDefaultText(rulerId)

        ruler.setValueListener {
            textView.text = "${it + getDefaultOffset(rulerId)} ${getUnit(rulerId)}"
            valueCallback.invoke(it)
        }
    }

    private fun getDefaultText(rulerId: Int): String {
        return when (rulerId) {
            R.id.heightPicker -> "${heightValue} cm"
            R.id.weightPicker -> "${weightValue} kg"
            R.id.targetWeightPicker -> "${targetWeightValue} kg"
            else -> ""
        }
    }

    private fun getDefaultOffset(rulerId: Int): Int {
        return when (rulerId) {
            R.id.heightPicker -> 100
            else -> 0
        }
    }

    private fun getUnit(rulerId: Int): String {
        return when (rulerId) {
            R.id.heightPicker -> "cm"
            R.id.weightPicker, R.id.targetWeightPicker -> "kg"
            else -> ""
        }
    }

    fun getHeightValue(): Float = heightValue
    fun getWeightValue(): Float = weightValue
    fun getTargetWeightValue(): Float = targetWeightValue
}
