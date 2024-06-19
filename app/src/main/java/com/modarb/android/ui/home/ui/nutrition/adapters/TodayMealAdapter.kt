package com.modarb.android.ui.home.ui.nutrition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemMealBinding
import com.modarb.android.ui.helpers.NutritionHelper
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Meal

class TodayMealAdapter(private val context: Context, private val mealList: List<Meal>) :
    RecyclerView.Adapter<TodayMealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealBinding.inflate(inflater, parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val mealItem = mealList[position]
        holder.bind(context, mealItem, position, mealList.size)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentType: String = ""

        fun bind(context: Context, mealItem: Meal, position: Int, totalCount: Int) {
            binding.mealName.text = mealItem.name
            binding.mealTake.text = "${mealItem.proteins}g"
            ViewUtils.loadImage(context, mealItem.image, binding.imageView25)

            if (currentType != mealItem.type) {
                if (position < totalCount - 1) {
                    binding.space.visibility = View.VISIBLE
                }
                binding.mealType.visibility = View.VISIBLE
                binding.mealType.text = mealItem.type
                currentType = mealItem.type
            } else {
                binding.space.visibility = View.GONE
                binding.mealType.visibility = View.GONE
            }
            itemView.setOnClickListener {
                NutritionHelper.showDetails(
                    context, NutritionHelper.buildNutritionString(mealItem.ingredients)
                )
            }
        }

    }


}
