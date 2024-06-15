package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.modarb.android.R
import com.modarb.android.databinding.ActivityExerciseInfoBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.workout.adapters.ExerciseInfoViewPagerAdapter

class ExerciseInfoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityExerciseInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
        setData()
        handleBackBtn()
        handleStartBtn()
        handleIntent()
    }

    private fun handleIntent() {
        val isInfo = intent.getBooleanExtra("info", false)
        if (isInfo) {
            binding.startBtn.visibility = View.GONE
        }
    }

    private fun handleStartBtn() {
        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }

    private fun handleBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        binding.exerciseTitle.text = WorkoutData.selectedExercise.name
        binding.exerciseCount.text =
            "Exercise " + (WorkoutData.selectedExerciseNumber.toString() + " / " + WorkoutData.getTodayWorkout()!!.exercises.size)

        if (WorkoutData.selectedExercise.duration > 0) {
            binding.exerciseSets.text = getString(R.string.timed_exercise)
        } else {
            binding.exerciseSets.text =
                (WorkoutData.selectedExercise.sets).toString() + " sets x " + WorkoutData.selectedExercise.reps + " reps"
        }
        ViewUtils.loadImage(
            this@ExerciseInfoActivity,
            WorkoutData.selectedExercise.media.url,
            binding.exerciseGIf
        )

    }


    private fun initViewPager() {
        val adapter =
            ExerciseInfoViewPagerAdapter(this@ExerciseInfoActivity, WorkoutData.selectedExercise)
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