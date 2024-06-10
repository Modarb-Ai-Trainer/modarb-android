package com.modarb.android.ui.home.ui.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.modarb.android.R
import com.modarb.android.databinding.FragmentNutritionBinding
import com.modarb.android.ui.home.ui.nutrition.adapters.NutritionViewPagerAdapter
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator


class NutritionFragment : Fragment() {

    private lateinit var binding: FragmentNutritionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViewPager()

        return root
    }

    private fun initViewPager(

    ) {


        val viewPager2 = ViewPager2ViewHeightAnimator()
        viewPager2.viewPager2 = binding.viewPager

        val adapter = NutritionViewPagerAdapter(requireContext())
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


}