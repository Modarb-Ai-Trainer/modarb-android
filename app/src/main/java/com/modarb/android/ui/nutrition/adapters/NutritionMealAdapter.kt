package com.modarb.android.ui.nutrition.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.nutrition.models.NutritionDataModel

class NutritionMealAdapter(private var dataList: List<NutritionDataModel>) :
    RecyclerView.Adapter<NutritionMealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_nutrition_meal, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDayName: TextView = itemView.findViewById(R.id.textViewDayName)
        private val textViewMealName: TextView = itemView.findViewById(R.id.textViewMealName)
        private val textViewMealDescription: TextView = itemView.findViewById(R.id.textViewMealDescription)
        private val textViewCalorieAmount: TextView = itemView.findViewById(R.id.textViewCalorieAmount)
        private val textViewCalorieUnit: TextView = itemView.findViewById(R.id.textViewCalorieUnit)
        private val textViewMealName2: TextView = itemView.findViewById(R.id.textViewMealName2)
        private val textViewMealDescription2: TextView = itemView.findViewById(R.id.textViewMealDescription2)
        private val textViewCalorieAmount2: TextView = itemView.findViewById(R.id.textViewCalorieAmount2)
        private val textViewCalorieUnit2: TextView = itemView.findViewById(R.id.textViewCalorieUnit2)
        private val textViewMealName3: TextView = itemView.findViewById(R.id.textViewMealName3)
        private val textViewMealDescription3: TextView = itemView.findViewById(R.id.textViewMealDescription3)
        private val textViewCalorieAmount3: TextView = itemView.findViewById(R.id.textViewCalorieAmount3)
        private val textViewCalorieUnit3: TextView = itemView.findViewById(R.id.textViewCalorieUnit3)
        private val textViewMealName4: TextView = itemView.findViewById(R.id.textViewMealName4)
        private val textViewMealDescription4: TextView = itemView.findViewById(R.id.textViewMealDescription4)
        private val textViewCalorieAmount4: TextView = itemView.findViewById(R.id.textViewCalorieAmount4)
        private val textViewCalorieUnit4: TextView = itemView.findViewById(R.id.textViewCalorieUnit4)

        fun bind(data: NutritionDataModel) {
            textViewDayName.text = data.dayName
            textViewMealName.text = data.mealName
            textViewMealDescription.text = data.mealDescription
            textViewCalorieAmount.text = data.calorieAmount
            textViewCalorieUnit.text = data.calorieUnit
            textViewMealName2.text = data.mealName2
            textViewMealDescription2.text = data.mealDescription2
            textViewCalorieAmount2.text = data.calorieAmount2
            textViewCalorieUnit2.text = data.calorieUnit2
            textViewMealName3.text = data.mealName3
            textViewMealDescription3.text = data.mealDescription3
            textViewCalorieAmount3.text = data.calorieAmount3
            textViewCalorieUnit3.text = data.calorieUnit3
            textViewMealName4.text = data.mealName4
            textViewMealDescription4.text = data.mealDescription4
            textViewCalorieAmount4.text = data.calorieAmount4
            textViewCalorieUnit4.text = data.calorieUnit4
        }
    }
}