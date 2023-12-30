package com.modarb.android.ui.onboarding.ViewPagerViews

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.modarb.android.R

class SelectFitnessLevelView(private var view: View, private var ctx: Context) {

    init {
        initializeFeatureViews(
            R.id.container1,
            R.id.containerOneTitle,
            R.id.containerOneDesc,
            R.string.beginner,
            R.string.beginnerDesc
        )
        initializeFeatureViews(
            R.id.container2,
            R.id.containerTwoTitle,
            R.id.containerTwoDesc,
            R.string.intermediate,
            R.string.intermediateDesc
        )
        initializeFeatureViews(
            R.id.container3,
            R.id.containerThreeTitle,
            R.id.containerThreeDesc,
            R.string.advanced,
            R.string.advancedDesc
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
        questionTitle.text = ctx.getString(R.string.fitness_level)
        title.text = ctx.getString(titleStr)
        desc.visibility = View.VISIBLE
        desc.text = ctx.getString(descStr)
    }


    private fun setOnClickListenerForViews(
        container: LinearLayout,
        firstItem: LinearLayout,
        secondItem: LinearLayout,
        thirdItem: LinearLayout
    ) {
        container.setOnClickListener {
            when (container.id) {
                R.id.container1 -> {
                    setViewBackgrounds(
                        secondItem, firstItem, thirdItem
                    )
                }

                R.id.container2 -> {
                    setViewBackgrounds(
                        thirdItem, firstItem, secondItem
                    )
                }

                R.id.container3 -> {
                    setViewBackgrounds(
                        firstItem, secondItem, thirdItem
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