package com.modarb.android.ui.onboarding.ViewPagerViews

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.modarb.android.R


class SelectExercisePlaceView(private var view: View, private var ctx: Context) {

    init {
        initializeFeatureViews(
            R.id.container1,
            R.id.containerOneTitle,
            R.string.gym,
        )
        initializeFeatureViews(
            R.id.container2,
            R.id.containerTwoTitle,
            R.string.home,
        )
        initializeFeatureViews(
            R.id.container3,
            R.id.containerThreeTitle,
            R.string.both,
        )
    }

    private fun initializeFeatureViews(
        containerId: Int, titleId: Int, titleStr: Int
    ) {
        val container = view.findViewById<LinearLayout>(containerId)
        val title = view.findViewById<TextView>(titleId)
        initViews(
            container,
            title,
            titleStr,
            view.findViewById(R.id.container3),
            view.findViewById(R.id.container1),
            view.findViewById(R.id.container2)
        )
    }


    private fun initViews(
        container: LinearLayout,
        title: TextView,
        titleStr: Int,
        getFitterView: LinearLayout,
        loseWeightView: LinearLayout,
        gainMuscleView: LinearLayout
    ) {
        container.layoutParams.height = 180
        title.gravity = Gravity.CENTER

        setTitle(title, titleStr)
        setOnClickListenerForViews(container, getFitterView, loseWeightView, gainMuscleView)
    }

    private fun setTitle(
        title: TextView, titleStr: Int
    ) {
        val questionTitle = view.findViewById<TextView>(R.id.questionTitle)
        questionTitle.text = ctx.getString(R.string.exercise_place)
        title.text = ctx.getString(titleStr)
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