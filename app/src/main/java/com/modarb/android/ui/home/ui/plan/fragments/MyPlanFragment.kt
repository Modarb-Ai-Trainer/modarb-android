package com.modarb.android.ui.home.ui.plan.fragments

import ExercisesAddAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.FragmentMyPlanBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.adapters.MyPlanViewPagerAdapter
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.persentation.PlanViewModel
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyPlanFragment : Fragment() {

    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var selectExerciseBottomSheet: BottomSheetDialog
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var binding: FragmentMyPlanBinding
    private lateinit var exercisesAdapter: ExercisesAddAdapter
    private lateinit var currentSelectedAdapter: ExercisesAddAdapter

    private lateinit var exercisesData: ExercisesResponse
    private var selectedFilter: String = ""
    private lateinit var progressBar: ProgressBar
    private lateinit var currentSelectedRecyclerView: RecyclerView


    @SuppressLint("NotifyDataSetChanged")
    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyPlanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toggleButtonGroup.check(R.id.myPlanBtn)

        observeData()
        initBottomSheet()
        handleAddCustomWorkout()
        getCustomWorkouts()
        observeCombinedResponses()
        exercisesAdapter = ExercisesAddAdapter(requireContext(), true)
        initSelectBottomSheet()
        Log.e("workoutID", WorkoutData.workoutId)

        return root
    }


    private fun observeData() {
        binding.progress.progressOverlay.visibility = View.VISIBLE

        planViewModel.getPlanPage(
            WorkoutData.workoutId, "Bearer " + UserPrefUtil.getUserData(requireContext())!!.token
        )
    }

    private fun observeCombinedResponses() {
        lifecycleScope.launch {
            planViewModel.combinedResponses.collect { combined ->
                val planResponse = combined.first
                val customWorkoutsResponse = combined.second

                if (planResponse is ApiResult.Success && customWorkoutsResponse is ApiResult.Success) {
                    handleBothSuccess(
                        planResponse.data, customWorkoutsResponse.data
                    )
                }
            }
        }
    }

    private fun handleBothSuccess(
        planPageResponse: PlanPageResponse, customWorkoutResponse: CustomWorkoutResponse
    ) {
        Log.d("My plan page success", "Nice work")
        initViewPager(planPageResponse, customWorkoutResponse)
        WorkoutData.weekList = planPageResponse.data.weeks
        binding.progress.progressOverlay.visibility = View.GONE

    }


    private fun handlePlanFail(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
        binding.progress.progressOverlay.visibility = View.GONE
    }

    private fun handlePlanError(planPageResponse: PlanPageResponse) {
        WorkoutData.weekList = planPageResponse.data.weeks
        binding.progress.progressOverlay.visibility = View.GONE
    }


    private fun handleAddCustomWorkout() {
        binding.addCustomWorkOut.setOnClickListener {
            bottomSheet.show()
        }
    }

    private fun getCustomWorkouts() {
        lifecycleScope.launch {
            planViewModel.customWorkoutResponse.collect {
                when (it) {
                    is ApiResult.Error<*> -> handlePlanError(it as PlanPageResponse)
                    is ApiResult.Failure -> handlePlanFail(it.exception)
                    else -> {}
                }
            }
        }
        planViewModel.getCustomWorkouts("Bearer ${UserPrefUtil.getUserData(requireContext())?.token}")
    }

    private fun getSearchResultData(search: String) {
        planViewModel.searchExercise(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}", search, selectedFilter
        )

        lifecycleScope.launch {
            planViewModel.getExercise.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleSearchResult(it.data as ExercisesResponse)
                    is ApiResult.Failure -> handlePlanFail(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleSearchResult(exercisesResponse: ExercisesResponse) {
        if (exercisesResponse.data.isEmpty()) return
        exercisesData = exercisesResponse
        exercisesAdapter.updateData(this, exercisesData.data)
        //Log.d("Search Data", exercisesResponse.data[0].name)
    }

    private fun getExercises() {
        val token = "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"

        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            planViewModel.getPaginatedExercises(token, selectedFilter).collectLatest { pagingData ->
                exercisesAdapter.submitData(pagingData)
            }
        }
//
//        lifecycleScope.launch {
//            planViewModel.isLoading.collect { isLoading ->
//                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//            }
//        }

    }

    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.add_excersice_view)

        val closeBtn: ImageButton? = bottomSheet.findViewById(com.modarb.android.R.id.closeBtn)
        val addExercise: View? = bottomSheet.findViewById(R.id.addExerciseView)
        currentSelectedRecyclerView = bottomSheet.findViewById(R.id.recyclerView)!!

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

        closeBtn?.setOnClickListener {
            exercisesAdapter.clearSelectedData()
            currentSelectedAdapter.clearData(this)
            bottomSheet.hide()
        }


        addExercise?.setOnClickListener {
            selectExerciseBottomSheet.show()
        }
    }

    private fun showSelectedExercise(recyclerView: RecyclerView) {
        currentSelectedAdapter = ExercisesAddAdapter(requireContext(), false)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = currentSelectedAdapter
        currentSelectedAdapter.updateData(this, exercisesAdapter.getSelectedData())
    }


    private fun initSelectBottomSheet() {

        selectExerciseBottomSheet = BottomSheetDialog(requireContext())
        selectExerciseBottomSheet.setContentView(R.layout.select_exersice_view)

        val closeBtn: ImageButton? =
            selectExerciseBottomSheet.findViewById(com.modarb.android.R.id.closeBtn)
        val addButton: Button = selectExerciseBottomSheet.findViewById(R.id.addBtn)!!
        val recyclerView: RecyclerView? =
            selectExerciseBottomSheet.findViewById(com.modarb.android.R.id.recyclerView)
        progressBar = selectExerciseBottomSheet.findViewById(R.id.progress)!!

        val searchEditText: EditText? = selectExerciseBottomSheet.findViewById(R.id.searchEditText)
        val spinner: Spinner? = selectExerciseBottomSheet.findViewById(R.id.typeSpinner)

        selectExerciseBottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        selectExerciseBottomSheet.setOnShowListener {
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

        // TODO handle clear when swipe down
        closeBtn?.setOnClickListener {
            selectExerciseBottomSheet.hide()
        }
        addButton.setOnClickListener {
            showSelectedExercise(currentSelectedRecyclerView)
            selectExerciseBottomSheet.hide()
        }

        initBottomSheetRecycle(recyclerView!!)
        initBottomSheetSpinner(spinner!!)
        handleSearch(searchEditText!!)
        handleOnClickOnSpinner(spinner)

    }

    private fun initBottomSheetSpinner(spinner: Spinner) {
        val spinnerAdapter = ArrayAdapter(
            requireContext(), R.layout.custom_spinner_item, WorkoutData.getBodyParts()
        )
        spinner.adapter = spinnerAdapter

        spinner.setSelection(0)
    }

    private fun handleOnClickOnSpinner(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                selectedFilter = selectedItem
                if (selectedFilter == "All") selectedFilter = ""
                exercisesAdapter.clearData(this@MyPlanFragment)
                getExercises()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun initBottomSheetRecycle(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = exercisesAdapter
    }


    private fun handleSearch(searchEditText: EditText) {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val searchTerm = searchEditText.text.trim().toString()
                Log.d("searchTermLen", searchTerm.length.toString())
                if (searchTerm.isEmpty()) {
                    getExercises()
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

    private fun initViewPager(
        planResponse: PlanPageResponse, customWorkoutResponse: CustomWorkoutResponse
    ) {


        val viewPager2 = ViewPager2ViewHeightAnimator()
        viewPager2.viewPager2 = binding.viewPager

        val adapter = MyPlanViewPagerAdapter(requireContext(), planResponse, customWorkoutResponse)
        viewPager2.viewPager2!!.adapter = adapter

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.myPlanBtn -> viewPager2.viewPager2!!.currentItem = 0
                    R.id.customWorkOut -> viewPager2.viewPager2!!.currentItem = 1
                }
            }
        }

        viewPager2.viewPager2!!.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.myPlanBtn
                        1 -> R.id.customWorkOut
                        else -> View.NO_ID
                    }
                )
                if (position == 0) {
                    binding.addCustomWorkOut.hide()
                    bottomSheet.hide()
                } else {
                    binding.addCustomWorkOut.show()
                }
            }
        })
    }


}
