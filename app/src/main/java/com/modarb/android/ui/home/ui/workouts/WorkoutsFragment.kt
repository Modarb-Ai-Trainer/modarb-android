package com.modarb.android.ui.home.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.modarb.android.R
import com.modarb.android.databinding.FragmentWorkoutsBinding
import com.modarb.android.ui.home.ui.workouts.adapters.WorkoutsViewPagerAdapter

class WorkoutsFragment : Fragment() {


    companion object {
        fun newInstance() = WorkoutsFragment()
    }

    private lateinit var viewModel: WorkoutsViewModel
    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        initViewPager()
        return binding.root
    }

    private fun initViewPager() {

        val adapter = WorkoutsViewPagerAdapter(requireContext())
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
                if (position == 0) {
                    binding.flipBody.show()
                } else {
                    binding.flipBody.hide()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
