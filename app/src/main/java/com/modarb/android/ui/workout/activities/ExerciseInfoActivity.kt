package com.modarb.android.ui.workout.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.modarb.android.R
import com.modarb.android.databinding.ActivityExerciseInfoBinding
import com.modarb.android.ui.workout.adapters.ExerciseInfoViewPagerAdapter

class ExerciseInfoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityExerciseInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }

    private fun setData() {

    }

    private fun initViewPager() {
        val adapter = ExerciseInfoViewPagerAdapter(this@ExerciseInfoActivity)
        binding.viewPager.adapter = adapter

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.targetMuscleBtn -> binding.viewPager.currentItem = 0
                    R.id.instructionsMuscleBtn -> binding.viewPager.currentItem = 1
                    R.id.equipmentBtn -> binding.viewPager.currentItem = 2
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.targetMuscleBtn
                        1 -> R.id.instructionsMuscleBtn
                        2 -> R.id.equipmentBtn
                        else -> View.NO_ID
                    }
                )
            }
        })
    }


}