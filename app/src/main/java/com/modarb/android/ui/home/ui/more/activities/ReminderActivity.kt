package com.modarb.android.ui.home.ui.more.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.modarb.android.R

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val mealsSwitch: SwitchCompat = findViewById(R.id.mealsSwitch)
        val workoutSwitch: SwitchCompat = findViewById(R.id.workoutSwitch)
        val challengesSwitch: SwitchCompat = findViewById(R.id.challengesSwitch)
        val feedPostsSwitch: SwitchCompat = findViewById(R.id.feedPostsSwitch)


        mealsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mealsSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            } else {
                mealsSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_75))
            }
        }

        workoutSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                workoutSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            } else {
                workoutSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_75))
            }
        }

        challengesSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                challengesSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            } else {
                challengesSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_75))
            }
        }

        feedPostsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                feedPostsSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))
            } else {
                feedPostsSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_75))
            }
        }

    }
}