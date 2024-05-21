package com.modarb.android.ui.home.ui.plan.adapters

import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ViewPager2ViewHeightAnimator {

    var viewPager2: ViewPager2? = null
    set(value) {
            if (field != value) {
                field?.unregisterOnPageChangeCallback(onPageChangeCallback)
                field = value
                value?.registerOnPageChangeCallback(onPageChangeCallback)
            }
        }

    private val layoutManager: LinearLayoutManager? get() = (viewPager2?.getChildAt(0) as? RecyclerView)?.layoutManager as? LinearLayoutManager

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            recalculate(position, positionOffset)
        }
    }

    fun recalculate(position: Int, positionOffset: Float = 0f) = layoutManager?.apply {
        val leftView = findViewByPosition(position) ?: return@apply
        val rightView = findViewByPosition(position + 1)
        val setMeasure = {
            viewPager2?.apply {
                val leftHeight = getMeasuredViewHeightFor(leftView)
                layoutParams = layoutParams.apply {
                    height = if (rightView != null) {
                        val rightHeight = getMeasuredViewHeightFor(rightView)
                        leftHeight + ((rightHeight - leftHeight) * positionOffset).toInt()
                    } else {
                        leftHeight
                    }
                }
                invalidate()
            }
        }
        val onLayoutChanged =
            ViewTreeObserver.OnGlobalLayoutListener {
                setMeasure.invoke()
            }
        leftView.viewTreeObserver.addOnGlobalLayoutListener(onLayoutChanged)
        rightView?.viewTreeObserver?.addOnGlobalLayoutListener(onLayoutChanged)
        setMeasure.invoke()
    }

    private fun getMeasuredViewHeightFor(view: View): Int {
        val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(wMeasureSpec, hMeasureSpec)
        return view.measuredHeight
    }
}