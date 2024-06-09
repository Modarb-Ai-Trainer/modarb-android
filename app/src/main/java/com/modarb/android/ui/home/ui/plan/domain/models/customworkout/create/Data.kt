package com.modarb.android.ui.home.ui.plan.domain.models.customworkout.create

data class Data(
    val creationDate: String,
    val exercises: List<String>,
    val id: String,
    val name: String,
    val user: String
)