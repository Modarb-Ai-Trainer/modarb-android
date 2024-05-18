package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.modarb.android.R
import com.modarb.android.databinding.ActivityWorkoutBinding
import com.modarb.android.network.Result
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.modarb.android.ui.workout.ExerciseListener
import com.modarb.android.ui.workout.adapters.ExercisePagerAdapter
import com.modarb.android.ui.workout.presentation.WorkoutViewModel
import kotlinx.coroutines.launch


class WorkoutActivity : AppCompatActivity(), ExerciseListener {

    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var adapter: ExercisePagerAdapter
    private val workoutViewModel: WorkoutViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        disableViewPagerScroll()
        handleNavigationButtons()
        incrementSetCount()
        observeViewModel()
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            workoutViewModel.workoutStatus.collect { result ->
                when (result) {
                    is Result.Success<*> -> handleSuccess(result.data as BaseResponse)
                    is Result.Failure -> handleFailure(result.exception)
                    else -> {}
                }
            }
        }


    }

    private fun handleSuccess(data: BaseResponse) {
        Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun handleFailure(exception: Throwable) {
        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
    }

    private fun initViewPager() {
        adapter = ExercisePagerAdapter(this, this)
        binding.exercisePager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.exercisePager)
    }

    private fun markWorkoutDone() {
        val myWorkoutId = WorkoutData.workoutId
        val week = WorkoutData.getCurrentWeek()!!.week_number
        val day = WorkoutData.getTodayWorkout()!!.day_number
        val token = "Bearer ${UserPrefUtil.getUserData(this)?.token}"
        workoutViewModel.markWorkoutDone(myWorkoutId, week, day, token)
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
            } else {
                markWorkoutDone()
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun disableViewPagerScroll() {
        binding.exercisePager.setOnTouchListener { arg0, arg1 -> true }
    }

    override fun onCloseListener() {
        finish()
    }


}