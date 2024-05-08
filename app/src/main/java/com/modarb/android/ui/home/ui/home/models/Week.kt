package com.modarb.android.ui.home.ui.home.models

data class Week(
    val days: List<Day>,
    val is_done: Boolean,
    val week_number: Int
)