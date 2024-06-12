package com.modarb.android.ui.home.ui.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.FragmentNutritionBinding
import com.modarb.android.ui.home.ui.nutrition.adapters.NutritionViewPagerAdapter
import com.modarb.android.ui.home.ui.plan.adapters.ViewPager2ViewHeightAnimator


class NutritionFragment : Fragment(), OnMealClickListener {

    private lateinit var binding: FragmentNutritionBinding
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var bottomSheetProgressBar: ProgressBar

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

    private fun initBottomSheetRecycle(recyclerView: RecyclerView) {
    }

    override fun onMailClick(mealType: String) {
        initAddMealBottomSheet(mealType)
        bottomSheet.show()
    }


}