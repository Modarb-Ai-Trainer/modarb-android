package com.modarb.android.ui.home.ui.plan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageButton
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
import com.modarb.android.databinding.FragmentMyPlanBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.adapters.ExercisesAddAdapter
import com.modarb.android.ui.home.ui.plan.adapters.MyPlanViewPagerAdapter
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.persentation.PlanViewModel
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.modarb.android.ui.workout.domain.models.WorkoutModel
import kotlinx.coroutines.launch


class MyPlanFragment : Fragment() {

    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var selectExerciseBottomSheet: BottomSheetDialog
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var binding: FragmentMyPlanBinding


    @SuppressLint("NotifyDataSetChanged")
    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyPlanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toggleButtonGroup.check(com.modarb.android.R.id.myPlanBtn)

        observeData()
        initBottomSheet()
        handleAddCustomWorkout()
        initSelectBottomSheet()
        getCustomWorkouts()
        observeCombinedResponses()
        Log.e("workoutID", WorkoutData.workoutId)

        return root
    }


    private fun observeData() {
        binding.progress.progressOverlay.visibility = View.VISIBLE

//        lifecycleScope.launch {
//            planViewModel.planResponse.collect {
//                when (it) {
//                    is ApiResult.Error<*> -> handlePlanError(it as PlanPageResponse)
//                    is ApiResult.Failure -> handlePlanFail(it.exception)
//                    else -> {}
//                }
//            }
//        }

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


    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(com.modarb.android.R.layout.add_excersice_view)

        val closeBtn: ImageButton? = bottomSheet.findViewById(com.modarb.android.R.id.closeBtn)
        val addExercise: View? = bottomSheet.findViewById(com.modarb.android.R.id.addExerciseView)

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
            bottomSheet.hide()
        }


        addExercise?.setOnClickListener {
            selectExerciseBottomSheet.show()
        }
    }


    private fun initSelectBottomSheet() {
        // TODO refactor this shit
        selectExerciseBottomSheet = BottomSheetDialog(requireContext())
        selectExerciseBottomSheet.setContentView(com.modarb.android.R.layout.select_exersice_view)

        val closeBtn: ImageButton? =
            selectExerciseBottomSheet.findViewById(com.modarb.android.R.id.closeBtn)
        val recyclerView: RecyclerView? =
            selectExerciseBottomSheet.findViewById(com.modarb.android.R.id.recyclerView)

        val spinner: Spinner? =
            selectExerciseBottomSheet.findViewById(com.modarb.android.R.id.typeSpinner)

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

        closeBtn?.setOnClickListener {
            selectExerciseBottomSheet.hide()
        }

        recyclerView!!.layoutManager = LinearLayoutManager(context)

        val data = mutableListOf<WorkoutModel>()

        for (i in 0..3) {
            data.add(WorkoutModel(1, "test", "test", "test", i + 1))
        }

        val adapter = ExercisesAddAdapter(data)
        recyclerView.adapter = adapter
        val items = arrayOf("All", "Body", "Chest")

        val spinnerAdapter = ArrayAdapter(
            requireContext(), com.modarb.android.R.layout.custom_spinner_item, items
        )
        spinner!!.setSelection(0)

        spinner.adapter = spinnerAdapter
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
                    com.modarb.android.R.id.myPlanBtn -> viewPager2.viewPager2!!.currentItem = 0
                    com.modarb.android.R.id.customWorkOut -> viewPager2.viewPager2!!.currentItem = 1
                }
            }
        }

        viewPager2.viewPager2!!.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toggleButtonGroup.check(
                    when (position) {
                        0 -> com.modarb.android.R.id.myPlanBtn
                        1 -> com.modarb.android.R.id.customWorkOut
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
