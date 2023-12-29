package com.modarb.android.ui.onboarding.helpers

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.modarb.android.R

class SelectGoalView(private var view: View, private var ctx: Context) {

    init {
        initializeFeatureViews(
            R.id.container1,
            R.id.containerOneTitle,
            R.id.containerOneDesc,
            R.string.lose_weight,
            R.string.want_to_burn_fats_and_lose_weight
        )
        initializeFeatureViews(
            R.id.container2,
            R.id.containerTwoTitle,
            R.id.containerTwoDesc,
            R.string.gain_muscle,
            R.string.want_to_gain_weight_and_or_build_muscle
        )
        initializeFeatureViews(
            R.id.container3,
            R.id.containerThreeTitle,
            R.id.containerThreeDesc,
            R.string.get_fitter,
            R.string.want_to_enhance_my_overall_physical_fitness
        )
    }

    private fun initializeFeatureViews(
        containerId: Int, titleId: Int, descId: Int, titleStr: Int, descStr: Int
    ) {
        val container = view.findViewById<LinearLayout>(containerId)
        val title = view.findViewById<TextView>(titleId)
        val desc = view.findViewById<TextView>(descId)

        initViews(
            container,
            title,
            desc,
            titleStr,
            descStr,
            view.findViewById(R.id.container3),
            view.findViewById(R.id.container1),
            view.findViewById(R.id.container2)
        )
    }


    private fun initViews(
        container: LinearLayout,
        title: TextView,
        desc: TextView,
        titleStr: Int,
        descStr: Int,
        getFitterView: LinearLayout,
        loseWeightView: LinearLayout,
        gainMuscleView: LinearLayout
    ) {
        setTitleAndDescription(title, desc, titleStr, descStr)

        setOnClickListenerForViews(container, getFitterView, loseWeightView, gainMuscleView)
    }

    private fun setTitleAndDescription(
        title: TextView, desc: TextView, titleStr: Int, descStr: Int
    ) {
        val questionTitle = view.findViewById<TextView>(R.id.questionTitle)
        questionTitle.text = ctx.getString(R.string.whats_your_fitness_goal)
        title.text = ctx.getString(titleStr)
        desc.text = ctx.getString(descStr)
    }

    private fun setOnClickListenerForViews(
        container: LinearLayout,
        getFitterView: LinearLayout,
        loseWeightView: LinearLayout,
        gainMuscleView: LinearLayout
    ) {
        container.setOnClickListener {
            when (container.id) {
                // handle on clicks
                R.id.container1 -> {
                    setViewBackgrounds(
                        loseWeightView, getFitterView, gainMuscleView
                    )
                }

                R.id.container2 -> {
                    setViewBackgrounds(
                        gainMuscleView, getFitterView, loseWeightView
                    )
                }

                R.id.container3 -> {
                    setViewBackgrounds(
                        getFitterView, loseWeightView, gainMuscleView
                    )
                }
            }
        }
    }

    private fun setViewBackgrounds(selectedView: LinearLayout, vararg otherViews: LinearLayout) {
        selectedView.setBackgroundResource(R.drawable.neon_blue_shape)
        otherViews.forEach { it.setBackgroundResource(R.drawable.neon_blue_edges) }
    }

}