package com.modarb.android.ui.home.ui.nutrition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.FragmentNutritionBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.adapters.NutritionViewPagerAdapter
import com.modarb.android.ui.home.ui.nutrition.domain.models.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.persentation.NutritionViewModel
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.launch


class NutritionFragment : Fragment(), OnMealClickListener {

    private lateinit var binding: FragmentNutritionBinding
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var bottomSheetProgressBar: ProgressBar
    private lateinit var viewModel: NutritionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(NutritionViewModel::class.java)

        initViewPager()
        getMeals()
        return root
    }

    private fun initViewPager(

    ) {
        val viewPager2 = ViewPager2ViewHeightAnimator()
        viewPager2.viewPager2 = binding.viewPager

        val adapter = NutritionViewPagerAdapter(requireContext(), this)
        viewPager2.viewPager2!!.adapter = adapter

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.myDailyRoutine -> viewPager2.viewPager2!!.currentItem = 0
                    R.id.plansBtn -> viewPager2.viewPager2!!.currentItem = 1
                }
            }
        }

        viewPager2.viewPager2!!.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.myDailyRoutine
                        1 -> R.id.plansBtn
                        else -> View.NO_ID
                    }
                )

            }
        })
    }

    private fun initAddMealBottomSheet(mealName: String) {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.all_meals_bottom_sheet)
        val closeBtn: ImageButton? = bottomSheet.findViewById(R.id.closeBtn)
        val recyclerView: RecyclerView = bottomSheet.findViewById(R.id.recyclerView)!!
        val searchEditText: EditText = bottomSheet.findViewById(R.id.searchEditText)!!
        val mealType: TextView = bottomSheet.findViewById(R.id.mealName)!!
        bottomSheetProgressBar = bottomSheet.findViewById(R.id.bottomSheetProgressBar)!!
        bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        mealType.text = mealName
        initBottomSheetRecycle(recyclerView)
        handleSearch(searchEditText)
        closeBtn?.setOnClickListener { bottomSheet.hide() }
    }

    private fun handleSearch(searchEditText: EditText) {

    }

    private fun getMeals() {
        viewModel.getTodayMeals(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )

        viewModel.getDailyGoals(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )
        viewModel.getTodayInTake(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )

        viewModel.getMyMealPlan(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )

        viewModel.getAllMealsPlans(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )

        lifecycleScope.launch {
            viewModel.getDailyGoalsResponse.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        val response = it.data as DailyGoalsResponse
                        Log.e("dailyGoal", response.data.stepsGoal.toString())
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }


        lifecycleScope.launch {
            viewModel.getTodayInTake.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        val response = it.data as TodayInTakeResponse
                        Log.e("todayInTake", response.data.fatGoal.toString())
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.todayMealsResponse.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleMealsOK(it.data as TodayMealsResponse)
                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getMyMealPlan.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        val response = it.data as MyMealPlanResponse
                        Log.e("MyMealPlanPage", response.data.meal_plan.your_journey)
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getAllMealsPlans.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        val response = it.data as AllMealsPlansResponse
                        Log.e("AllMealPlanPage", response.data[0].description)
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleMealsOK(exercisesResponse: TodayMealsResponse) {
        Log.e("mealsData", exercisesResponse.data.meal_plan.description)
    }

    private fun handleFail(exception: Throwable) {
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()

    }

    private fun initBottomSheetRecycle(recyclerView: RecyclerView) {
    }

    override fun onMailClick(mealType: String) {
        initAddMealBottomSheet(mealType)
        bottomSheet.show()
    }


}