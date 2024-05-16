package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R
import com.modarb.android.databinding.ActivityWorkoutBinding
import com.modarb.android.ui.workout.adapters.ExercisePagerAdapter


class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var adapter: ExercisePagerAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        disableViewPagerScroll()
        handleNavigationButtons()
        incrementSetCount()
    }

    private fun incrementSetCount() {
        // TODO handle this when they fix the api
        //if (adapter.isTimedExercise(currentPosition)) return
        binding.incButton.setOnClickListener {
            val currentPosition = binding.exercisePager.currentItem
            val currentView = binding.exercisePager.findViewWithTag<View>("view$currentPosition")
            adapter.incSetCount(currentPosition, currentView)
        }
    }

    private fun initViewPager() {
        adapter = ExercisePagerAdapter(this)
        binding.exercisePager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.exercisePager)
    }

    private fun handleNavigationButtons() {
        binding.prevButton.setOnClickListener {
            val currentItem = binding.exercisePager.currentItem
            if (currentItem > 0) {
                binding.exercisePager.setCurrentItem(currentItem - 1, true)
            }
        }

        binding.nextButton.setOnClickListener {
            if (!adapter.isExerciseDone(binding.exercisePager.currentItem)) {
                Toast.makeText(
                    this, getString(R.string.complete_the_exercise_first), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val currentItem = binding.exercisePager.currentItem
            val adapterCount = adapter.count - 1
            if (currentItem < adapterCount) {
                binding.exercisePager.setCurrentItem(currentItem + 1, true)
                adapter.logExercise(binding.exercisePager.currentItem)
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun disableViewPagerScroll() {
        binding.exercisePager.setOnTouchListener { arg0, arg1 -> true }
    }


}