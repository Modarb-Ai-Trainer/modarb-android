package com.modarb.android.ui.home.ui.nutrition.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.ItemNutritionMealDayBinding
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.Meal

class NutritionMealDayAdapter(
    private var dataList: List<Meal>, private var context: Context
) : RecyclerView.Adapter<NutritionMealDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNutritionMealDayBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(position, data, context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(
        private val binding: ItemNutritionMealDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int, data: Meal, context: Context) {
            loadImageBasedOnPosition(position)
            binding.textViewMealName.text = data.name
            binding.textViewCalorieAmount.text = data.calories.toString() + " Kcal"

            var desc = ""
            for (i in data.ingredients.indices) {
                desc += data.ingredients[i].name
                if (i != data.ingredients.size - 1) {
                    desc += "\n"
                }
            }
            binding.textViewMealDescription.text = desc

            // TODO check feedback
//            binding.root.setOnClickListener {
//                NutritionHelper.showDetails(
//                    context,
//                    NutritionHelper.buildNutritionString(data.ingredients[position])
//                )
//            }
        }

        private fun loadImageBasedOnPosition(position: Int) {
            val drawableResId = when (position) {
                1 -> R.drawable.lunch_meal
                2 -> R.drawable.snack_meal
                3 -> R.drawable.dinner_meal
                else -> R.drawable.breakfast_meal
            }

            binding.backgroundImageView.setImageResource(drawableResId)
        }

    }
}