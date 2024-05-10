package com.modarb.android.ui.home.helpers

import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.home.ui.plan.models.Week

object WorkoutData {

    var workoutId: String = ""

    lateinit var weekList: List<Week>
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

}