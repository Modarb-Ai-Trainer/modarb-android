package com.modarb.android.ui.onboarding.ViewPagerViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.models.UserRegisterData
import com.modarb.android.ui.onboarding.utils.CustomViews.RulerView

class SelectTargetWeightView(private val view: View, private val context: Context) {

    private var heightValue: Float = 170.0f
    private var weightValue: Float = 78.0f
    private var targetWeightValue: Float = 78.0f

    init {
        initRuler(R.id.heightPicker, R.id.heightTxt, "height") { heightValue = it + 100 }
        initRuler(R.id.weightPicker, R.id.weightTxt, "weight") { weightValue = it }
        initRuler(R.id.targetWeightPicker, R.id.targetTxt, "target") { targetWeightValue = it }
    }

    @SuppressLint("SetTextI18n")
    private fun initRuler(rulerId: Int, textId: Int, type: String, valueCallback: (Float) -> Unit) {
        val ruler: RulerView = view.findViewById(rulerId)
        val textView: TextView = view.findViewById(textId)

        textView.text = getDefaultText(rulerId)

        UserRegisterData.registerRequest.height = getHeightValue().toInt()
        UserRegisterData.registerRequest.weight = getWeightValue().toInt()
        UserRegisterData.registerRequest.preferences.target_weight = getTargetWeightValue().toInt()

        ruler.setValueListener {
            textView.text = "${it + getDefaultOffset(rulerId)} ${getUnit(rulerId)}"
            when (type) {
                "height" -> {
                    UserRegisterData.registerRequest.height = 100 + it.toInt()
                }

                "weight" -> {
                    UserRegisterData.registerRequest.weight = it.toInt()
                }

                "target" -> {
                    UserRegisterData.registerRequest.preferences.target_weight = it.toInt()
                }

            }
            Log.d("Ruler", "initRuler:${type} = ${it.toInt()}")
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
