package com.modarb.android.ui.nutrition.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.nutrition.adapters.NutritionMealAdapter
import com.modarb.android.ui.nutrition.models.NutritionDataModel

class NutritionGuidePlansActivity : AppCompatActivity() {

    private lateinit var nutritionMealAdapter: NutritionMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_guide_plans)

        val nutritionPlanCardView: CardView = findViewById(R.id.nutritionPlanCardView)
        nutritionPlanCardView.setOnClickListener {
            val intent = Intent(this, AboutNutritionPlanActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.nutritionMealRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val dataList = prepareData()
        nutritionMealAdapter = NutritionMealAdapter(dataList)
        recyclerView.adapter = nutritionMealAdapter
    }

    private fun prepareData(): List<NutritionDataModel> {
        val dayNames = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
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
