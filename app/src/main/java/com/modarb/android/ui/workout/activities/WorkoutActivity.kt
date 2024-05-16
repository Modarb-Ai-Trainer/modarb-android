package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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


        binding.doneButton.setOnClickListener {
            val currentPosition = binding.exercisePager.currentItem
            adapter.incSetCount(currentPosition)
        }

    }

    private fun initViewPager() {

        adapter = ExercisePagerAdapter(this, binding.exercisePager)
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
            val currentItem = binding.exercisePager.currentItem
            val adapterCount = adapter.count - 1
            if (currentItem < adapterCount) {
                binding.exercisePager.setCurrentItem(currentItem + 1, true)
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun disableViewPagerScroll() {
        binding.exercisePager.setOnTouchListener { arg0, arg1 -> true }

    }


}