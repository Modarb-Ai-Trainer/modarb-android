package com.modarb.android.ui.home.ui.nutrition

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.AllMealsBottomSheetBinding
import com.modarb.android.databinding.FragmentNutritionBinding
import com.modarb.android.databinding.SelectedIngredientViewBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.adapters.IngredientsAdapter
import com.modarb.android.ui.home.ui.nutrition.adapters.NutritionViewPagerAdapter
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.modarb.android.ui.home.ui.nutrition.models.Ingredient
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.Data
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse
import com.modarb.android.ui.home.ui.nutrition.presentation.NutritionData
import com.modarb.android.ui.home.ui.nutrition.presentation.NutritionViewModel
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class NutritionFragment : Fragment(), OnMealClickListener {

    private lateinit var binding: FragmentNutritionBinding
    private lateinit var ingreadientsBottomSheet: BottomSheetDialog
    private lateinit var saveMealBottomSheet: BottomSheetDialog

    private lateinit var bottomSheetProgressBar: ProgressBar
    private lateinit var viewModel: NutritionViewModel
    private lateinit var ingredientsAdapter: IngredientsAdapter

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
        viewModel = ViewModelProvider(this)[NutritionViewModel::class.java]

        viewModel.getAllNutritionData("Bearer ${UserPrefUtil.getUserData(requireContext())!!.token}")
    }

    private fun initViewPager(
        todayMealsResponse: TodayMealsResponse,
        todayInTakeResponse: TodayInTakeResponse,
        allMealsResponse: AllMealsPlansResponse,
        myMealsResponse: MyMealPlanResponse,
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


    private fun initAddIngredientsBottomSheet(mealName: String) {
        ingreadientsBottomSheet = BottomSheetDialog(requireContext())

        val binding = AllMealsBottomSheetBinding.inflate(LayoutInflater.from(context))
        ingreadientsBottomSheet.setContentView(binding.root)

        ingreadientsBottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        ingreadientsBottomSheet.setOnShowListener {
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

        binding.saveMealBtn.setOnClickListener {
            if (ingredientsAdapter.getSelectedData().isEmpty()) {
                Toast.makeText(
                    context, getString(R.string.please_select_ingredients), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            initSaveMealDialog(mealName)
        }
        initBottomSheetRecycle(mealName, binding.recyclerView)
        binding.mealName.text = mealName
        handleSearch(binding.searchEditText)
        binding.closeBtn.setOnClickListener { ingreadientsBottomSheet.hide() }
        bottomSheetProgressBar = binding.bottomSheetProgressBar
        ingreadientsBottomSheet.show()
    }


    private fun handleSearch(searchEditText: EditText) {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val searchTerm = searchEditText.text.trim().toString()
                Log.d("searchTermLen", searchTerm.length.toString())
                if (searchTerm.isEmpty()) {
                    getIngredients()
                } else {
                    getSearchResultData(searchTerm)
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {


            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {


            }
        })
    }

    private fun getSearchResultData(searchTerm: String) {
        viewModel.searchIngredients(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}", searchTerm
        )


        lifecycleScope.launch {
            viewModel.getAllIngredients.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleSearchResult(it.data as IngredientsResponse)
                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleSearchResult(ingredientsResponse: IngredientsResponse) {
        if (ingredientsResponse.data.isEmpty()) return
        ingredientsAdapter.updateData(this, ingredientsResponse.data)
        bottomSheetProgressBar.visibility = View.GONE
    }

    private fun getIngredients() {
        val token = "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"

        lifecycleScope.launch {
            viewModel.getPaginatedExercises(token).collectLatest { pagingData ->
                ingredientsAdapter.submitData(pagingData)
            }
        }
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
        return nutritionData.todayMeals != null && nutritionData.todayInTake != null && nutritionData.myMealPlan != null && nutritionData.allMealsPlans != null
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


        if (todayMealsResponse != null && todayInTakeResponse != null && allMealsResponse != null && myMealsResponse != null) {
            initViewPager(
                todayMealsResponse!!,
                todayInTakeResponse!!,
                allMealsResponse!!,
                myMealsResponse!!,
            )
        }
    }


    private fun handleFail(exception: Throwable) {
        Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
        Log.e("fail", exception.stackTrace.toString())
    }

    private fun initBottomSheetRecycle(mealType: String, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        ingredientsAdapter = IngredientsAdapter(mealType, requireContext(), true)
        recyclerView.adapter = ingredientsAdapter

        ingredientsAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                bottomSheetProgressBar.visibility = View.VISIBLE
            } else {
                bottomSheetProgressBar.visibility = View.GONE

                val errorState = when {
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    override fun onMailClick(mealType: String) {
        initAddIngredientsBottomSheet(mealType)
        ingreadientsBottomSheet.show()
        getIngredients()
    }


    private fun initSaveMealDialog(mealName: String) {
        saveMealBottomSheet = BottomSheetDialog(requireContext())
        val binding = SelectedIngredientViewBinding.inflate(LayoutInflater.from(context))
        saveMealBottomSheet.setContentView(binding.root)
        saveMealBottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        saveMealBottomSheet.setOnShowListener {
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

        binding.tvName.text = mealName
        initSelectedIngredients(mealName, binding.recyclerView)
        binding.btnClose.setOnClickListener { saveMealBottomSheet.hide() }
        setMealData(binding, ingredientsAdapter.getSelectedData())
        binding.confirmButton.setOnClickListener {
            addMealRequest()
        }
        saveMealBottomSheet.show()
    }

    private fun initSelectedIngredients(mealType: String, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = IngredientsAdapter(mealType, requireContext(), false)
        recyclerView.adapter = adapter
        adapter.updateData(this, ingredientsAdapter.getSelectedData())
    }

    @SuppressLint("SetTextI18n")
    private fun setMealData(binding: SelectedIngredientViewBinding, ingredients: List<Data>) {
        var totalFats = 0.0f
        var totalCarbs = 0.0f
        var totalProtein = 0.0f
        var totalCalories = 0.0f

        for (ingredient in ingredients) {
            totalFats += ingredient.fats
            totalCarbs += ingredient.carbs
            totalProtein += ingredient.proteins
            totalCalories += ingredient.calories
        }

        binding.tvCarbsValue.text = totalCarbs.toString() + "g"
        binding.tvProteinValue.text = totalProtein.toString() + "g"
        binding.tvFatsValue.text = totalFats.toString() + "g"
        binding.tvCaloriesValue.text = totalCalories.toString() + "Kcal"
    }

    private fun addMealRequest() {

        val list: ArrayList<Ingredient> = ArrayList()

        for (ingredient in ingredientsAdapter.getSelectedData()) {
            list.add(Ingredient(ingredient.id, ingredient.servings_count))
        }
        var data = AddCustomMealBody(list)
        viewModel.addCustomMeal(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}", data
        )


        lifecycleScope.launch {
            viewModel.addCustomMeal.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        Toast.makeText(
                            context, getString(R.string.meal_added_successfully), Toast.LENGTH_SHORT
                        ).show()
                        saveMealBottomSheet.hide()
                        ingreadientsBottomSheet.hide()
                        requestData()
                    }

                    is ApiResult.Failure -> handleFail(it.exception)
                    else -> {}
                }
            }
        }
    }


}