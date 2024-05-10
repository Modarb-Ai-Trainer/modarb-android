package com.modarb.android.ui.home.helpers

import com.modarb.android.ui.home.ui.plan.models.Day
import com.modarb.android.ui.home.ui.plan.models.Week

object WorkoutData {

    var workoutId: String = ""
    var currentWeekPosition: Int = 0

    lateinit var weekList: List<Week>

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

}