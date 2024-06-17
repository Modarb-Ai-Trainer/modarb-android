package com.modarb.android.ui.helpers

import android.app.AlertDialog
import android.content.Context
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Ingredient

object NutritionHelper {
    fun showDetails(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message).setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    fun buildNutritionString(item: Ingredient): String {
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