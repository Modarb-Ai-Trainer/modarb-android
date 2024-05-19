package com.modarb.android.ui.home.ui.plan.domain.models.customworkout

data class Data(
    val creationDate: String,
    val exercises: List<Exercise>,
    val id: String,
    val name: String,
    val user: String
)