package com.modarb.android.ui.home.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.modarb.android.R
import com.modarb.android.databinding.FragmentHomeBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.network.NetworkHelper
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.HomeActivity
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.home.presentation.HomeViewModel
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.persentation.PlanViewModel
import com.modarb.android.ui.onboarding.activities.SplashActivity
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.modarb.android.ui.workout.activities.TodayWorkoutActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val planViewModel: PlanViewModel by viewModels()


    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getHomeData()
        getTodayInTake()
        initLogout()
        initActions()
        handleClick()

        Log.d("User ID", UserPrefUtil.getUserData(requireContext())!!.user.id)
        return root
    }

    private fun handleClick() {
        binding.continueBtn.setOnClickListener {
            startActivity(Intent(requireContext(), TodayWorkoutActivity::class.java))
        }
    }

    private fun getTodayInTake() {
        homeViewModel.getTodayInTake("Bearer " + UserPrefUtil.getUserData(requireContext())!!.token)


        lifecycleScope.launch {
            homeViewModel.todayInTake.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleTodayInTakeResponse(it.data as TodayInTakeResponse)
                    is ApiResult.Failure -> handleHomeFail(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleTodayInTakeResponse(todayInTakeResponse: TodayInTakeResponse) {
        updateProgressBars(todayInTakeResponse)
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgressBars(model: TodayInTakeResponse) {
        val data = model.data
        updateProgressBar(
            binding.trackerView.circularProgressBar,
            data.caloriesGoal,
            data.caloriesGoal - data.caloriesLeft
        )
        binding.trackerView.calValue.text =
            (data.caloriesGoal - data.caloriesLeft).toString() + " / " + data.caloriesGoal + "\n\nKcal"
        binding.trackerView.burnedVal.text = data.caloriesBurned.toString() + "\nBurned"
        binding.trackerView.leftVal.text = data.caloriesLeft.toString() + "\nLeft"

        updateMacroProgressBar(
            binding.trackerView.carbsProgressBar,
            binding.trackerView.carbsValue,
            data.carbsGoal,
            data.carbsConsumed,
            "g"
        )
        updateMacroProgressBar(
            binding.trackerView.proteinProgressBar,
            binding.trackerView.proteinValue,
            data.proteinGoal,
            data.proteinConsumed,
            "g"
        )
        updateMacroProgressBar(
            binding.trackerView.fatsProgressBar,
            binding.trackerView.fatsValue,
            data.fatGoal,
            data.fatConsumed,
            "g"
        )

    }

    private fun updateProgressBar(progressBar: ProgressBar, max: Int, progress: Int) {
        progressBar.max = max
        progressBar.progress = progress
    }

    private fun updateProgressBar(progressBar: CircularProgressBar, max: Int, progress: Int) {
        progressBar.progressMax = max.toFloat()
        progressBar.progress = progress.toFloat()
    }

    private fun updateMacroProgressBar(
        progressBar: ProgressBar, valueTextView: TextView, goal: Int, consumed: Int, unit: String
    ) {
        valueTextView.text = "$consumed/$goal$unit"
        progressBar.max = goal
        progressBar.progress = consumed
    }

    private fun initLogout() {
        binding.profileBtn.setOnClickListener {
            UserPrefUtil.saveUserData(requireContext(), null)
            UserPrefUtil.setUserLoggedIn(requireContext(), false)
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }

        binding.viewMealBtn.setOnClickListener {
            (activity as? HomeActivity)?.navigateToFragment(R.id.navigation_nutrition)
        }
    }


    private fun getHomeData() {
        binding.progressView.progressOverlay.visibility = View.VISIBLE
        homeViewModel.getUserHomePage("Bearer " + UserPrefUtil.getUserData(requireContext())!!.token)


        lifecycleScope.launch {
            homeViewModel.homeResponse.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleHomeSuccess(it.data as HomePageResponse)
                    is ApiResult.Error -> handleHomeError(it.data as HomePageResponse)
                    is ApiResult.Failure -> handleHomeFail(it.exception)
                    else -> {}
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initMealPlan(homePageResponse: HomePageResponse) {
        binding.mealDetails.text =
            homePageResponse.data.myMealPlan.today.numberOfMeals.toString() + " Meals and ${homePageResponse.data.myMealPlan.today.numberOfSnacks} snacks"
        binding.calories.text =
            homePageResponse.data.myMealPlan.today.totalCalories.toString() + " Cal"
    }

    private fun handleHomeFail(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
        binding.progressView.progressOverlay.visibility = View.GONE
    }

    private fun handleHomeSuccess(res: HomePageResponse) {
        WorkoutData.workoutId = res.data.myWorkout.id
        setData(res)
        initMealPlan(res)
        getPlanData()
    }

    private fun handleHomeError(errorResponse: HomePageResponse) {
        NetworkHelper.showErrorMessage(requireContext(), errorResponse)
        binding.progressView.progressOverlay.visibility = View.GONE
    }

    private fun getPlanData() {

        lifecycleScope.launch {
            planViewModel.planResponse.collect {
                when (it) {
                    is ApiResult.Success<*> -> handlePlanSuccess(it.data as PlanPageResponse)
                    is ApiResult.Error -> handlePlanError(it.data)
                    is ApiResult.Failure -> handlePlanFail(it.exception)
                    else -> {}
                }
            }
        }
        planViewModel.getPlanPage(
            WorkoutData.workoutId, "Bearer " + UserPrefUtil.getUserData(requireContext())!!.token
        )

    }

    private fun handlePlanFail(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
    }

    private fun handlePlanError(errorResponse: PlanPageResponse) {
        NetworkHelper.showErrorMessage(requireContext(), errorResponse)
    }

    private fun handlePlanSuccess(res: PlanPageResponse) {
        WorkoutData.weekList = res.data.weeks
        binding.progressView.progressOverlay.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun initActions() {
        binding.workoutPlanView.setOnClickListener {
            (activity as? HomeActivity)?.navigateToFragment(R.id.navigation_my_plan)
        }

        binding.nutritionView.setOnClickListener {
            (activity as? HomeActivity)?.navigateToFragment(R.id.navigation_nutrition)
        }

        binding.userName.text = "Hey, \n" + UserPrefUtil.getUserData(requireContext())!!.user.name
    }

    @SuppressLint("SetTextI18n")
    private fun setData(response: HomePageResponse) {
        binding.todayWorkoutName.text = response.data.myWorkout.workout.name
        binding.workouttime.text =
            formatWorkoutTime(response.data.myWorkout.workout.min_per_day, requireContext())
        binding.exerciseCountTxt.text =
            WorkoutData.getTodayWorkout(response.data.myWorkout.weeks)?.total_number_exercises.toString() + " " + getString(
                R.string.exercise
            )
    }

    private fun formatWorkoutTime(minutesPerDay: Int, context: Context): String {
        return "$minutesPerDay ${context.getString(R.string.min)}"
    }

}