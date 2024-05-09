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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.modarb.android.R
import com.modarb.android.databinding.FragmentHomeBinding
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.home.logic.HomeRepository
import com.modarb.android.ui.home.ui.home.logic.HomeViewModel
import com.modarb.android.ui.home.ui.home.logic.HomeViewModelFactory
import com.modarb.android.ui.home.ui.home.models.Day
import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.onboarding.activities.SplashActivity
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.modarb.android.ui.workout.activities.TodayWorkoutActivity

class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViewModels()
        getHomeData()
        initLogout()
        initActions()
        handleClick()

        Log.e("User ID", UserPrefUtil.getUserData(requireContext())!!.user.id)
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

    private fun initViewModels() {
        val homeRepository = HomeRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this, HomeViewModelFactory(homeRepository)
        )[HomeViewModel::class.java]
    }

    private fun getTodayWorkout(): Day? {
        val weekList = viewModel.homeResponse.value!!.body()!!.data.myWorkout.weeks

        for (week in weekList) {
            if (!week.is_done) {
                for (day in week.days) {
                    if (!day.is_done) {
                        return day
                    }
                }
                break
            }
        }
        return null
    }


    private fun getHomeData() {
        binding.progressView.progressOverlay.visibility = View.VISIBLE
        viewModel.getUserHomePage(requireContext())

        viewModel.homeResponse.observe(requireActivity()) { response ->
            RetrofitService.handleRequest(response = response, onSuccess = { res ->
                setData(res)
                WorkoutData.workoutId = res.data.myWorkout.id
            }, onError = { errorResponse ->
                val defaultErrorMessage = getString(R.string.an_error_occurred)
                val message = errorResponse?.errors?.firstOrNull() ?: errorResponse?.error
                ?: defaultErrorMessage
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            })
            binding.progressView.progressOverlay.visibility = View.GONE
        }
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
            getTodayWorkout()?.total_number_exercises.toString() + " " + getString(R.string.exercise)
    }

    private fun formatWorkoutTime(minutesPerDay: Int, context: Context): String {
        return "$minutesPerDay ${context.getString(R.string.min)}"
    }

}