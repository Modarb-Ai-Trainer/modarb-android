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
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.presentation.NutritionData
import com.modarb.android.ui.home.ui.nutrition.presentation.NutritionViewModel
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


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
        requestData()
        collectData()
        return root
    }

    private fun requestData() {
        viewModel = ViewModelProvider(this).get(NutritionViewModel::class.java)

        viewModel.getAllNutritionData("Bearer ${UserPrefUtil.getUserData(requireContext())!!.token}")
    }

    private fun initViewPager(
        todayMealsResponse: TodayMealsResponse,
        todayInTakeResponse: TodayInTakeResponse,
        allMealsResponse: AllMealsPlansResponse,
        myMealsResponse: MyMealPlanResponse,
        dailyGoalsResponse: DailyGoalsResponse
    ) {
        val viewPager2 = ViewPager2ViewHeightAnimator()
        viewPager2.viewPager2 = binding.viewPager

        val adapter = NutritionViewPagerAdapter(
            requireContext(),
            this,
            todayMealsResponse,
            todayInTakeResponse,
            allMealsResponse,
            myMealsResponse,
            dailyGoalsResponse
        )
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

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.combinedNutritionData.collect { nutritionData ->
                if (isAllDataLoaded(nutritionData)) {
                    handleCombinedData(nutritionData)
                }
            }
        }
    }

    private fun isAllDataLoaded(nutritionData: NutritionData): Boolean {
        return nutritionData.todayMeals != null && nutritionData.dailyGoals != null && nutritionData.todayInTake != null && nutritionData.myMealPlan != null && nutritionData.allMealsPlans != null
    }

    private fun handleCombinedData(nutritionData: NutritionData) {

        fun <T : Any> handleApiResult(
            type: KClass<T>, result: ApiResult<T>?, onSuccess: (T) -> Unit
        ) {
            when (result) {
                is ApiResult.Success -> {
                    onSuccess(result.data)
                    binding.progressView.progressOverlay.visibility = View.GONE
                }

                is ApiResult.Failure -> {
                    val exception = result.exception
                    val className = exception::class.java.name
                    val genericClassName = type.simpleName
                    Log.e(
                        "Failure",
                        "API call failed in class: $className with exception: $exception and generic type: $genericClassName"
                    )
                    handleFail(exception)
                    binding.progressView.progressOverlay.visibility = View.GONE
                }

                else -> {}
            }
        }


        var todayMealsResponse: TodayMealsResponse? = null
        var todayInTakeResponse: TodayInTakeResponse? = null
        var allMealsResponse: AllMealsPlansResponse? = null
        var myMealsResponse: MyMealPlanResponse? = null
        var dailyGoalsResponse: DailyGoalsResponse? = null

        handleApiResult(TodayMealsResponse::class, nutritionData.todayMeals) {
            todayMealsResponse = it
        }
        handleApiResult(
            TodayInTakeResponse::class, nutritionData.todayInTake
        ) { todayInTakeResponse = it }
        handleApiResult(
            AllMealsPlansResponse::class, nutritionData.allMealsPlans
        ) { allMealsResponse = it }
        handleApiResult(MyMealPlanResponse::class, nutritionData.myMealPlan) {
            myMealsResponse = it
        }
        handleApiResult(DailyGoalsResponse::class, nutritionData.dailyGoals) {
            dailyGoalsResponse = it
        }

        if (todayMealsResponse != null && todayInTakeResponse != null && allMealsResponse != null && myMealsResponse != null && dailyGoalsResponse != null) {
            initViewPager(
                todayMealsResponse!!,
                todayInTakeResponse!!,
                allMealsResponse!!,
                myMealsResponse!!,
                dailyGoalsResponse!!
            )
        }
    }


    private fun handleFail(exception: Throwable) {
        Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
        Log.e("fail", exception.stackTrace.toString())

    }

    private fun initBottomSheetRecycle(recyclerView: RecyclerView) {
    }

    override fun onMailClick(mealType: String) {
        initAddMealBottomSheet(mealType)
        bottomSheet.show()
    }


}