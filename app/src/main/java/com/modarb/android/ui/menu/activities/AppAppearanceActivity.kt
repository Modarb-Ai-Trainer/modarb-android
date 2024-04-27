package com.modarb.android.ui.menu.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.modarb.android.R

class AppAppearanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_appearance)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }


        val checkBoxSystemDefault = findViewById<CheckBox>(R.id.checkBoxSystemDefault)
        val checkBoxLightTheme = findViewById<CheckBox>(R.id.checkBoxLightTheme)
        val checkBoxDarkTheme = findViewById<CheckBox>(R.id.checkBoxDarkTheme)

        checkBoxSystemDefault.setOnClickListener {
            if (checkBoxSystemDefault.isChecked) {
                checkBoxLightTheme.isChecked = false
                checkBoxDarkTheme.isChecked = false
                checkBoxSystemDefault.setButtonDrawable(R.drawable.theme_checkbox)
            } else {
                checkBoxSystemDefault.buttonDrawable = null
            }
        }

        checkBoxLightTheme.setOnClickListener {
            if (checkBoxLightTheme.isChecked) {
                checkBoxSystemDefault.isChecked = false
                checkBoxDarkTheme.isChecked = false
                checkBoxLightTheme.setButtonDrawable(R.drawable.theme_checkbox)
            } else {
                checkBoxLightTheme.buttonDrawable = null
            }
        }

        checkBoxDarkTheme.setOnClickListener {
            if (checkBoxDarkTheme.isChecked) {
                checkBoxSystemDefault.isChecked = false
                checkBoxLightTheme.isChecked = false
                checkBoxDarkTheme.setButtonDrawable(R.drawable.theme_checkbox)
            } else {
                checkBoxDarkTheme.buttonDrawable = null
            }
        }
    }
}