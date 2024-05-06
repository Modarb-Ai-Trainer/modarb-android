package com.modarb.android.ui.home.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.modarb.android.ui.home.ui.home.logic.HomeRepository
import com.modarb.android.ui.home.ui.home.logic.HomeViewModel
import com.modarb.android.ui.home.ui.home.logic.HomeViewModelFactory
import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.onboarding.activities.SplashActivity
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var viewModel: HomeViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViewModels()
        getHomeData()
        initLogout()
        initActions()
        return root
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


    private fun getHomeData() {
        viewModel.getUserHomePage(requireContext())

        viewModel.homeResponse.observe(requireActivity()) { response ->
            RetrofitService.handleRequest(response = response, onSuccess = { res ->
                setData(res)
            }, onError = { errorResponse ->
                val defaultErrorMessage = getString(R.string.an_error_occurred)
                val message = errorResponse?.errors?.firstOrNull() ?: errorResponse?.error
                ?: defaultErrorMessage
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            })
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

        binding.todayWorkoutName.text = response.data.workout.name
        binding.time.text = formatWorkoutTime(response.data.workout.min_per_day, requireContext())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatWorkoutTime(minutesPerDay: Int, context: Context): String {
        return "$minutesPerDay ${context.getString(R.string.min)}"
    }

}