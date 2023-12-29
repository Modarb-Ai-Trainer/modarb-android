package com.modarb.android.ui.onboarding.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import com.modarb.android.R

class TargetWeightSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.target_weight_selection_view)
        var rulerValuePicker = findViewById<RulerValuePicker>(R.id.heightPicker);
        var cm = findViewById<TextView>(R.id.heightTxt);

        rulerValuePicker.selectValue(150);
        rulerValuePicker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {

            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                cm.text = "$selectedValue cm"

            }
        })
    }
}
