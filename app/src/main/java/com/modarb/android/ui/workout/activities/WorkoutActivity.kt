package com.modarb.android.ui.workout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.modarb.android.R
import com.modarb.android.databinding.ActivityWorkoutBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.helpers.WorkoutData
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
        checkButtonState()
        observeViewModel()
    }

    private fun checkButtonState() {
        val currentPosition = binding.exercisePager.currentItem
        val currentView = binding.exercisePager.findViewWithTag<View>("view$currentPosition")

        Log.d("is Timed", adapter.isTimedExercise(currentPosition).toString())
        if (adapter.isTimedExercise(currentPosition)) {
            binding.incButton.setImageResource(R.drawable.ic_play)
        } else {
            binding.incButton.setImageResource(R.drawable.ic_done_button)
        }
    }

    private fun incrementSetCount() {
        binding.incButton.setOnClickListener {
            val currentPosition = binding.exercisePager.currentItem
            val currentView = binding.exercisePager.findViewWithTag<View>("view$currentPosition")
            if (adapter.isTimedExercise(currentPosition)) {
                if (!adapter.isStarted(currentPosition)) {
                    adapter.initTimer(currentPosition, currentView)
                    adapter.startTimer(currentPosition)
                    binding.incButton.setImageResource(R.drawable.ic_pause)
                } else {
                    binding.incButton.setImageResource(R.drawable.ic_play)
                    adapter.pauseTimer(currentPosition)
                }
            } else {
                Toast.makeText(this, "Go for the next exercise !", Toast.LENGTH_SHORT).show()
                adapter.markDone(currentPosition)
                //adapter.incSetCount(currentPosition, currentView)
            }

        }
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            workoutViewModel.workoutStatus.collect { result ->
                when (result) {
                    is ApiResult.Success<*> -> handleSuccess(result.data as BaseResponse)
                    is ApiResult.Failure -> handleFailure(result.exception)
                    else -> {}
                }
                binding.progress.progressOverlay.visibility = View.GONE
            }
        }
    }

    private fun handleSuccess(data: BaseResponse) {
        Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
        val i = Intent(this, WorkoutInsightsActivity::class.java)
        startActivity(i)
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
        binding.progress.progressOverlay.visibility = View.VISIBLE
        val myWorkoutId = WorkoutData.workoutId
        val week = WorkoutData.getCurrentWeek()!!.week_number
        val day = WorkoutData.getTodayWorkout()!!.day_number
        val token = "Bearer ${UserPrefUtil.getUserData(this)?.token}"
        workoutViewModel.markWorkoutDone(myWorkoutId, week, day, token)
    }

    private fun handleNavigationButtons() {
        adapter.startSound(-1)
        adapter.startHelpSound(0)
        binding.prevButton.setOnClickListener {
            val currentItem = binding.exercisePager.currentItem
            if (currentItem > 0) {
                binding.exercisePager.setCurrentItem(currentItem - 1, true)
                checkButtonState()
            }
        }

        binding.nextButton.setOnClickListener {
            val currentItem = binding.exercisePager.currentItem

            if (adapter.isTimedExercise(currentItem)) {
                if (!adapter.isTimedExerciseDone(currentItem)) {
                    Toast.makeText(
                        this, getString(R.string.complete_the_exercise_first), Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

            } else if (!adapter.isExerciseDone(currentItem)) {
                Toast.makeText(
                    this, getString(R.string.complete_the_exercise_first), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            adapter.startSound(currentItem)
            val adapterCount = adapter.count - 1
            if (currentItem < adapterCount) {
                binding.exercisePager.setCurrentItem(currentItem + 1, true)
                adapter.logExercise(binding.exercisePager.currentItem)
                adapter.startHelpSound(binding.exercisePager.currentItem)
                checkButtonState()
            } else {
                markWorkoutDone()
            }
        }

    }

    private fun handleExerciseVoice() {

    }

    @SuppressLint("ClickableViewAccessibility")
    fun disableViewPagerScroll() {
        binding.exercisePager.setOnTouchListener { arg0, arg1 -> true }
    }

    override fun onCloseListener() {
        finish()
    }


}