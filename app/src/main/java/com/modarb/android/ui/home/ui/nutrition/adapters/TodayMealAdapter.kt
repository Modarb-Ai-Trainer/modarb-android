package com.modarb.android.ui.home.ui.nutrition.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemMealBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Ingredient
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
                showDetails(context, buildNutritionString(mealItem.ingredients[position]))
            }
        }

        private fun showDetails(context: Context, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message).setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }

        private fun buildNutritionString(item: Ingredient): String {
            val stringBuilder = StringBuilder()

            stringBuilder.append("Name: ${item.name}\n\n")
            stringBuilder.append("Calories: ${item.calories} kcal\n\n")
            stringBuilder.append("Carbs: ${item.carbs} g\n\n")
            stringBuilder.append("Fats: ${item.fats} g\n\n")
            stringBuilder.append("Proteins: ${item.proteins} g\n\n")
            stringBuilder.append("Serving Size: ${item.serving_size} g")
            return stringBuilder.toString()
        }


    }


}
