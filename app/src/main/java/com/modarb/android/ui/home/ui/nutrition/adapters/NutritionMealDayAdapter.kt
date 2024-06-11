package com.modarb.android.ui.home.ui.nutrition.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.ItemNutritionMealDayBinding
import com.modarb.android.ui.home.ui.nutrition.models.MealDayModel

class NutritionMealDayAdapter(private var dataList: List<MealDayModel>) :
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
        init {

        }

        fun bind(position: Int, data: MealDayModel) {
            when (position) {
                1 -> {
                    binding.backgroundImageView.setImageResource(R.drawable.lunch_meal)
                }

                2 -> {
                    binding.backgroundImageView.setImageResource(R.drawable.snack_meal)
                }

                3 -> {
                    binding.backgroundImageView.setImageResource(R.drawable.dinner_meal)
                }
            }
            binding.textViewMealName.text = data.mealName
        }
    }
}