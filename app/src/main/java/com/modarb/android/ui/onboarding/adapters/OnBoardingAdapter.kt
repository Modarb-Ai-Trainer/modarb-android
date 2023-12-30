package com.modarb.android.ui.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.ViewPagerViews.MessageView
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
            // 3 handle bmi
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

                3 -> {
                    MessageView(view, ctx, getBMIData())
                }
            }
        }
    }

    private fun getBMIData(): List<String> {
        val bmi = 22.23
        return listOf(
            "Great news! \uD83C\uDF89 Your BMI is ${bmi}, which falls within a healthy range. Your goal of reaching 58 kg is within reach, and we're here to support you every step of the way!",
            "Now, let's tailor your workouts to your fitness level and preferences.",
            "Your personalized journey awaits!"
        )
    }
}
