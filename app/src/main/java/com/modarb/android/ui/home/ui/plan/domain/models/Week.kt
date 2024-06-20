package com.modarb.android.ui.home.ui.plan.domain.models

data class Week(
    val days: List<Day>,
    val is_done: Boolean,
    val week_description: String,
    val week_name: String,
    val week_number: Int,
    var is_theCurrentWeek: Boolean = false
)