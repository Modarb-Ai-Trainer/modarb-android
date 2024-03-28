package com.modarb.android.ui.onboarding.adapters

import ItemSelectionView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.ViewPagerViews.MessageView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectExercisePlaceView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectFitnessLevelView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectGenderView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectGoalView
import com.modarb.android.ui.onboarding.ViewPagerViews.SelectTargetWeightView
import com.modarb.android.ui.onboarding.utils.Data

class OnBoardingAdapter(
    private val views: List<View>,
    val ctx: Context,
) : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    val viewStateMap: MutableMap<Int, Any> = mutableMapOf()

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
            val parent = view.parent as? ViewGroup
            parent?.removeView(view)

            container.addView(view)

            /*
             0 goal
             1 gender
             2 height and weight
             3 handle bmi
             4 fitness level
             5 exercise place
             6 equipments
             7 pain positions
             */

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
                    MessageView(view, ctx, Data.getBMIData())
                }

                4 -> {
                    SelectFitnessLevelView(view, ctx)
                }

                5 -> {
                    SelectExercisePlaceView(view, ctx)
                }

                6 -> {
                    initEquipView(view, type)
                }

                7 -> {
                    initInjView(view, type)
                }

                8 -> {
                    MessageView(view, ctx, Data.getCompleteMessages())
                }
            }

        }
    }

    fun initEquipView(view: View, id: Int) {
        if (viewStateMap.containsKey(id)) {
            viewStateMap[id]
        } else {
            viewStateMap[id] = ItemSelectionView(
                view,
                this.ctx,
                R.string.what_equipment_do_you_have,
                "preferred_equipment",
            )
        }
    }

    fun initInjView(view: View, id: Int) {
        if (viewStateMap.containsKey(id)) {
            viewStateMap[id]
        } else {
            ItemSelectionView(
                view,
                ctx,
                R.string.do_you_have_pain,
                "injuries",
            )
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
