package com.modarb.android.ui.home.ui.nutrition.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.DailyRoutineViewBinding
import com.modarb.android.databinding.PlansViewBinding
import com.modarb.android.ui.home.ui.nutrition.activities.AboutNutritionPlanActivity
import com.modarb.android.ui.home.ui.nutrition.models.NutritionDataModel

class NutritionViewPagerAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> DailyRoutineViewHolder(
                DailyRoutineViewBinding.inflate(inflater, parent, false)
            )

            1 -> PlansViewHolder(
                PlansViewBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailyRoutineViewHolder -> holder.bind(context)
            is PlansViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyRoutineViewHolder(private val binding: DailyRoutineViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context) {

        }
    }

    inner class PlansViewHolder(private val binding: PlansViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var nutritionMealAdapter: NutritionMealAdapter

        fun bind(context: Context) {
            binding.nutritionPlanCardView.setOnClickListener {
                val intent = Intent(context, AboutNutritionPlanActivity::class.java)
                context.startActivity(intent)
            }

            binding.nutritionMealRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val dataList = prepareData()
            nutritionMealAdapter = NutritionMealAdapter(dataList)
            binding.nutritionMealRecyclerView.adapter = nutritionMealAdapter
            binding.nutritionMealRecyclerView.isNestedScrollingEnabled = false

        }
    }

    private fun prepareData(): List<NutritionDataModel> {
        val dayNames =
            listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val mealNames = listOf("Breakfast", "Lunch", "Snack", "Dinner")
        val mealDescriptions = listOf(
            "Bread\nPeanut butter\nOrange",
            "Fried chicken\nRice\nApple",
            "Protein bar\nMango juice",
            "Bread\nCheese\nApple"
        )
        val calorieAmounts = listOf("350", "270", "400", "200")
        val calorieUnits = listOf("Kcal", "Kcal", "Kcal", "Kcal")

        val dataList = mutableListOf<NutritionDataModel>()

        for (day in dayNames) {
            val dataModel = NutritionDataModel(
                day,
                mealNames[0],
                mealDescriptions[0],
                calorieAmounts[0],
                calorieUnits[0],
                mealNames[1],
                mealDescriptions[1],
                calorieAmounts[1],
                calorieUnits[1],
                mealNames[2],
                mealDescriptions[2],
                calorieAmounts[2],
                calorieUnits[2],
                mealNames[3],
                mealDescriptions[3],
                calorieAmounts[3],
                calorieUnits[3]
            )
            dataList.add(dataModel)
        }

        return dataList
    }
}
