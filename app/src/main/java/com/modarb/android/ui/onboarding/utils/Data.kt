package com.modarb.android.ui.onboarding.utils

import android.widget.TextView
import com.modarb.android.ui.onboarding.models.ItemSelectionModel
import java.util.Locale

class Data {

    companion object {
        val equipmentList: MutableList<ItemSelectionModel> = mutableListOf(
            ItemSelectionModel("Barbells"),
            ItemSelectionModel("Dumbbells"),
            ItemSelectionModel("Gym machines"),
            ItemSelectionModel("Resistance band"),
            ItemSelectionModel("Body weight")
        )

        val painPositionsList: MutableList<ItemSelectionModel> = mutableListOf(
            ItemSelectionModel("Neck"),
            ItemSelectionModel("Shoulders"),
            ItemSelectionModel("Back"),
            ItemSelectionModel("Arms"),
            ItemSelectionModel("Knees")
        )

        fun getBMIData(): List<String> {
            val bmi = 22.23
            return listOf(
                "Great news! \uD83C\uDF89 Your BMI is ${bmi}, which falls within a healthy range. Your goal of reaching 58 kg is within reach, and we're here to support you every step of the way!",
                "Now, let's tailor your workouts to your fitness level and preferences.",
                "Your personalized journey awaits!"
            )
        }

        fun getCompleteMessages(): List<String> {
            return listOf(
                "Great job completing the initial steps! Now, let's tailor your workout plan to match your fitness level, preferred locations, and available equipment for a truly personalized experience.\n",
                "Your journey to a healthier you is just a step away! \uD83D\uDE80\uD83D\uDCAA",
            )
        }

        fun getSelected(selected: TextView): String {
            var selectedText = selected.text.toString().trim().lowercase(Locale.ROOT)
            if (selectedText == "body weight") {
                selectedText = "bodyweight"
            }
            return selectedText
        }

        fun getSelected(selected: String): String {
            var selectedText = selected
            if (selectedText == "body weight") {
                selectedText = "bodyweight"
            }
            return selectedText.lowercase(Locale.ROOT)
        }
    }


}