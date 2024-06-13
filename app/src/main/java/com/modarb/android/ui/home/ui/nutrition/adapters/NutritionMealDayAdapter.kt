package com.modarb.android.ui.home.ui.nutrition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.ItemNutritionMealDayBinding
import com.modarb.android.ui.home.ui.nutrition.models.MealDayModel

class NutritionMealDayAdapter(
    private var dataList: List<MealDayModel>,
    private var context: Context
) :
    RecyclerView.Adapter<NutritionMealDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNutritionMealDayBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(position, data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(
        private val binding: ItemNutritionMealDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, data: MealDayModel) {
            loadImageBasedOnPosition(position)

            binding.textViewMealName.text = data.mealName
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