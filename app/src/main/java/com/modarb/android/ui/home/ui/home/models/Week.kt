package com.modarb.android.ui.home.ui.home.models

data class Week(
    val days: List<Day>,
    val is_done: Boolean,
    val week_description: String,
    val week_name: String,
    val week_number: Int
)