package com.modarb.android.ui.helpers

import android.app.AlertDialog
import android.content.Context
import com.modarb.android.R
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.Data
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MealPlan
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Ingredient

object NutritionHelper {
    lateinit var selectedProgram: Data
    lateinit var selectedMyProgram: MealPlan

    fun showDetails(context: Context, message: String) {
        val builder = AlertDialog.Builder(context, R.style.DarkAlertDialogTheme)
        builder.setMessage(message).setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun buildNutritionString(item: List<Ingredient>): String {
        val stringBuilder = StringBuilder()

        for (ingredient in item) {
            stringBuilder.append("Name: ${ingredient.name}\n\n")
            stringBuilder.append("- Calories: ${ingredient.calories} kcal\n")
            stringBuilder.append("- Carbs: ${ingredient.carbs} g\n")
            stringBuilder.append("- Fats: ${ingredient.fats} g\n")
            stringBuilder.append("- Proteins: ${ingredient.proteins} g\n")
            stringBuilder.append("- Serving Size: ${ingredient.serving_size} g")
            stringBuilder.append("\n\n")
        }

        return stringBuilder.toString()
    }


}