package com.modarb.android.ui.home.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.modarb.android.R
import com.modarb.android.databinding.FragmentHomeBinding
import com.modarb.android.network.Result
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.home.presentation.HomeViewModel
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


    private fun initLogout() {
        binding.profileBtn.setOnClickListener {
            UserPrefUtil.saveUserData(requireContext(), null)
            UserPrefUtil.setUserLoggedIn(requireContext(), false)
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
    }


    private fun getHomeData() {
        binding.progressView.progressOverlay.visibility = View.VISIBLE
        homeViewModel.getUserHomePage("Bearer " + UserPrefUtil.getUserData(requireContext())!!.token)


        lifecycleScope.launch {
            homeViewModel.homeResponse.collect {
                when (it) {
                    is Result.Success<*> -> handleHomeSuccess(it.data as HomePageResponse)
                    is Result.Failure -> handleHomeError(it.exception)
                    else -> {}
                }
            }
        }

    }

    private fun handleHomeSuccess(res: HomePageResponse) {
        WorkoutData.workoutId = res.data.myWorkout.id
        setData(res)
        getPlanData()
        binding.progressView.progressOverlay.visibility = View.GONE
    }

    private fun handleHomeError(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
        binding.progressView.progressOverlay.visibility = View.GONE
    }

    private fun getPlanData() {

        lifecycleScope.launch {
            planViewModel.planResponse.collect {
                when (it) {
                    is Result.Success<*> -> handlePlanSuccess(it.data as PlanPageResponse)
                    is Result.Failure -> handlePlanError(it.exception)
                    else -> {}
                }
            }
        }
        planViewModel.getPlanPage(
            WorkoutData.workoutId, "Bearer " + UserPrefUtil.getUserData(requireContext())!!.token
        )

    }

    private fun handlePlanError(exception: Throwable) {

        // TODO handle plan error
//        val defaultErrorMessage = getString(R.string.an_error_occurred)
//        val message = errResponse.errors?.firstOrNull() ?: errResponse.error ?: defaultErrorMessage
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()


    }

    private fun handlePlanSuccess(res: PlanPageResponse) {
        WorkoutData.weekList = res.data.weeks
    }

    private fun initActions() {

        binding.workoutPlanView.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.navigation_my_plan)
        }

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