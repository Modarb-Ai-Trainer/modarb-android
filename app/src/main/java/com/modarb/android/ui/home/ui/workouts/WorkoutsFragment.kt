package com.modarb.android.ui.home.ui.workouts

import ExercisesAdapter
import WorkoutsViewPagerAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.FragmentWorkoutsBinding
import com.modarb.android.databinding.WorkoutEnrollViewBinding
import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.HomeActivity
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.workouts.models.BodyParts
import com.modarb.android.ui.home.ui.workouts.models.Workout
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.Data
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse
import com.modarb.android.ui.home.ui.workouts.persentation.WorkoutViewModel
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WorkoutsFragment : Fragment(), OnBodyPartClickListener, OnWorkoutItemClickListener {


    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var exerciseInfoSheet: BottomSheetDialog

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var bottomSheetProgressBar: ProgressBar
    private lateinit var exercisesAdapter: ExercisesAdapter
    private var selectedFilter: String = ""
    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        initSearchExercisesBottomSheet()
        getWorkoutPrograms()
        return binding.root
    }

    private fun initViewPager(workoutProgramsResponse: WorkoutProgramsResponse) {

        val adapter =
            WorkoutsViewPagerAdapter(requireContext(), this, this, workoutProgramsResponse)
        binding.viewPager.adapter = adapter

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.exerciseLibBtn -> binding.viewPager.currentItem = 0
                    R.id.workOutPrograms -> binding.viewPager.currentItem = 1
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.toggleButtonGroup.check(
                    when (position) {
                        0 -> R.id.exerciseLibBtn
                        1 -> R.id.workOutPrograms
                        else -> View.NO_ID
                    }
                )

            }
        })
    }

    private fun getWorkoutPrograms() {
        viewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]

        viewModel.getWorkoutPrograms(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"
        )


        lifecycleScope.launch {
            viewModel.getWorkoutPrograms.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        initViewPager(it.data as WorkoutProgramsResponse)
                        binding.progress.progressOverlay.visibility = View.GONE
                    }

                    is ApiResult.Error -> {
                        binding.progress.progressOverlay.visibility = View.GONE
                    }

                    is ApiResult.Failure -> {
                        binding.progress.progressOverlay.visibility = View.GONE
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initSearchExercisesBottomSheet() {
        exercisesAdapter = ExercisesAdapter(requireContext())
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.exercise_all_workout_view)
        val closeBtn: ImageButton? = bottomSheet.findViewById(R.id.closeBtn)
        val recyclerView: RecyclerView = bottomSheet.findViewById(R.id.recyclerView)!!
        val searchEditText: EditText = bottomSheet.findViewById(R.id.searchEditText)!!
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
        initBottomSheetRecycle(recyclerView)
        handleSearch(searchEditText)
        closeBtn?.setOnClickListener { bottomSheet.hide() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBodyPartClick(bodyPart: BodyParts) {
        bottomSheet.show()
        selectedFilter = bodyPart.name
        getExercises(bodyPart.name)
    }

    private fun getExercises(selectedFilter: String) {

        val token = "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}"

        lifecycleScope.launch {
            viewModel.getPaginatedExercises(token, "category", selectedFilter.replace("All", ""))
                .collectLatest { pagingData ->
                    exercisesAdapter.submitData(pagingData)
                }
        }
        observeLoadingState()
    }

    private fun initBottomSheetRecycle(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = exercisesAdapter
    }

    private fun handleSearch(searchEditText: EditText) {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val searchTerm = searchEditText.text.trim().toString()
                Log.d("searchTermLen", searchTerm.length.toString())
                if (searchTerm.isEmpty()) {
                    getExercises(selectedFilter)
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

    private fun getSearchResultData(search: String) {
        viewModel.searchExercise(
            "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}", search, selectedFilter
        )


        lifecycleScope.launch {
            viewModel.getExercise.collect {
                when (it) {
                    is ApiResult.Success<*> -> handleSearchResult(it.data as ExercisesResponse)
                    is ApiResult.Failure -> handleSearchFailure(it.exception)
                    else -> {}
                }
            }
        }
    }

    private fun handleSearchFailure(exception: Throwable) {
        Log.e("Search Error", exception.message.toString())
    }

    private fun handleSearchResult(exercisesResponse: ExercisesResponse) {
        if (exercisesResponse.data.isEmpty()) return
        exercisesAdapter.clearData(this)
        exercisesAdapter.updateData(this, exercisesResponse.data)
        Log.d("Search Data", exercisesResponse.data[0].name)
    }

    private fun observeLoadingState() {
        exercisesAdapter.addLoadStateListener { loadState ->
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

    override fun onWorkoutItemClick(workoutData: Data) {
        initExerciseInfoButton(workoutData)
    }


    private fun updateTextViews(data: Data, binding: WorkoutEnrollViewBinding) {
        binding.planDesc.text = data.description
        binding.fitGoal.text = data.fitness_goal
        binding.fitLevel.text = data.fitness_level
        binding.workPlace.text = data.place.joinToString(separator = ", ")
        binding.workTime.text = "${data.min_per_day} ${getString(R.string.min)} / day"
        binding.workEquip.text = data.type
        binding.workDays.text = data.total_number_days.toString() + " " + getString(R.string.days)
        binding.planName.text = data.name
    }

    private fun initExerciseInfoButton(workoutData: Data) {
        val binding = WorkoutEnrollViewBinding.inflate(layoutInflater)

        exerciseInfoSheet = BottomSheetDialog(requireContext())
        exerciseInfoSheet.setContentView(binding.root)
        exerciseInfoSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        exerciseInfoSheet.setOnShowListener {
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
        updateTextViews(workoutData, binding)
        exerciseInfoSheet.show()

        binding.enrollButton.setOnClickListener {
            viewModel.enrollWorkoutProgram(
                "Bearer ${UserPrefUtil.getUserData(requireContext())?.token}", Workout(
                    workoutData.id
                )
            )
        }

        binding.closeBtn.setOnClickListener {
            exerciseInfoSheet.hide()
        }

        lifecycleScope.launch {
            viewModel.enrollWorkout.collect {
                when (it) {
                    is ApiResult.Success<*> -> {
                        exerciseInfoSheet.hide()
                        (activity as? HomeActivity)?.navigateToFragment(R.id.navigation_home)
                        Toast.makeText(requireContext(), "Enrolled", Toast.LENGTH_SHORT).show()
                    }

                    is ApiResult.Failure -> {
                        handleSearchFailure(it.exception)
                        exerciseInfoSheet.hide()
                    }

                    else -> {}
                }
            }
        }
    }


}
