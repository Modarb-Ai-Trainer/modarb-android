package com.modarb.android.ui.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectGenderView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectGoalView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectTargetWeightView

class OnBoardingAdapter(private val views: List<View>, val ctx: Context) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = views[position]
        holder.bind(view, position)
    }

    override fun getItemCount(): Int = views.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(view: View, type: Int) {
            val container = itemView.findViewById<FrameLayout>(R.id.container)
            container.removeAllViews()
            container.addView(view)
            // 0 goal
            // 1 gender
            // 2 height and weight
            when (type) {
                0 -> {
                    SelectGoalView(view, ctx)
                }

                1 -> {
                    SelectGenderView(view)
                }

                2 -> {
                    SelectTargetWeightView(view, ctx)
                }
            }
        }
    }
}
