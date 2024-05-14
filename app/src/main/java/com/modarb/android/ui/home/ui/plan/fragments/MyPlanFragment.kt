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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.FragmentMyPlanBinding
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.adapters.ExercisesAddAdapter
import com.modarb.android.ui.home.ui.plan.adapters.MyPlanViewPagerAdapter
import com.modarb.android.ui.home.ui.plan.logic.PlanRepository
import com.modarb.android.ui.home.ui.plan.logic.PlanViewModel
import com.modarb.android.ui.home.ui.plan.logic.PlanViewModelFactory
import com.modarb.android.ui.workout.models.WorkoutModel


class MyPlanFragment : Fragment() {

    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var selectExerciseBottomSheet: BottomSheetDialog
    lateinit var viewModel: PlanViewModel
    private lateinit var binding: FragmentMyPlanBinding


    @SuppressLint("NotifyDataSetChanged")
    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyPlanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toggleButtonGroup.check(R.id.myPlanBtn)

        initViewModels()
        getDataFromViewModel()
        initBottomSheet()
        handleAddCustomWorkout()
        initSelectBottomSheet()
        return root
    }

    private fun initViewModels() {
        Log.e("workoutID", WorkoutData.workoutId)
        val planRepository = PlanRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this, PlanViewModelFactory(planRepository)
        )[PlanViewModel::class.java]
    }


    private fun getDataFromViewModel() {
        viewModel.getPlanPage(requireContext())
        viewModel.planResponse.observe(viewLifecycleOwner) { response ->
            RetrofitService.handleRequest(response = response, onSuccess = { res ->
                initViewPager(viewModel)
                WorkoutData.weekList = viewModel.planResponse.value?.body()?.data!!.weeks
            }, onError = { errorResponse ->
                val defaultErrorMessage = getString(R.string.an_error_occurred)
                val message = errorResponse?.errors?.firstOrNull() ?: errorResponse?.error
                ?: defaultErrorMessage
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            })
            binding.progress.progressOverlay.visibility = View.GONE
        }
    }


    private fun handleAddCustomWorkout() {
        binding.addCustomWorkOut.setOnClickListener {
            bottomSheet.show()
        }
    }


    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.add_excersice_view)

        val closeBtn: ImageButton? = bottomSheet.findViewById(R.id.closeBtn)
        val addExercise: View? = bottomSheet.findViewById(R.id.addExerciseView)

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
        selectExerciseBottomSheet.setContentView(R.layout.select_exersice_view)

        val closeBtn: ImageButton? = selectExerciseBottomSheet.findViewById(R.id.closeBtn)
        val recyclerView: RecyclerView? = selectExerciseBottomSheet.findViewById(R.id.recyclerView)

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


        // spinner
        val items = arrayOf("All", "Body", "Chest")

        val spinnerAdapter = ArrayAdapter(
            requireContext(), R.layout.custom_spinner_item, items
        )
        spinner!!.setSelection(0)

        spinner.adapter = spinnerAdapter
    }

    private fun initViewPager(viewModel: PlanViewModel) {


        val adapter = MyPlanViewPagerAdapter(requireContext(), viewModel)
        binding.viewPager.adapter = adapter

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.myPlanBtn -> binding.viewPager.currentItem = 0
                    R.id.customWorkOut -> binding.viewPager.currentItem = 1
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
