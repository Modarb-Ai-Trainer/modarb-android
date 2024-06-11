package com.modarb.android.ui.helpers

import com.modarb.android.ui.home.ui.plan.domain.models.Day
import com.modarb.android.ui.home.ui.plan.domain.models.Exercise
import com.modarb.android.ui.home.ui.plan.domain.models.Week
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data

object WorkoutData {


    var workoutId: String = ""
    var currentWeekPosition: Int = 0

    lateinit var weekList: List<Week>
    lateinit var selectedExercise: Exercise
    var selectedExerciseNumber: Int = 1
    var selectedCustomExercisesList: MutableList<Data> = mutableListOf()

    fun getWeekDaysCount(): Int {
        return weekList[currentWeekPosition].days.size
    }

    fun getCurrentWeek(): Week? {
        for (week in weekList) {
            if (!week.is_done) {
                return week
            }
        }
        return null
    }

    fun getTodayWorkout(): Day? {

        for (week in weekList) {
            if (!week.is_done) {
                for (day in week.days) {
                    if (!day.is_done) {
                        return day
                    }
                }
                break
            }
        }
        return null
    }


    fun getTodayWorkout(weekList: List<Week>): Day? {
        for (week in weekList) {
            if (!week.is_done) {
                for (day in week.days) {
                    if (!day.is_done) {
                        return day
                    }
                }
                break
            }
        }
        return null
    }

    fun getTotalExerciseTime(exercise: List<com.modarb.android.ui.home.ui.plan.domain.models.customworkout.Exercise>): String {
        var totalTime = 0
        for (ex in exercise) {
            totalTime += ex.duration
        }
        return totalTime.toString()
    }


    fun getBodyParts(): Array<String> {
        return arrayOf(
            "All",
            "waist",
            "upper legs",
            "upper arms",
            "lower legs",
            "chest",
            "lower arms",
            "back",
            "neck",
            "shoulders",
            "cardio"
        )
    }

    fun getSelectedCustomExercises(): List<Data> {
        return selectedCustomExercisesList
    }



    // TODO handle getting reps after fixing the api
//    fun getTotalExerciseReps(exercise: List<com.modarb.android.ui.home.ui.plan.domain.models.customworkout.Exercise>): String {
//        var totalReps = 0
//        for (ex in exercise) {
//            totalReps += ex.
//        }
//        return totalReps.toString()
//    }


}