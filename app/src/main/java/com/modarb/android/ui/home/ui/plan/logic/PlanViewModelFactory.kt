package com.modarb.android.ui.home.ui.plan.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlanViewModelFactory(private val repository: PlanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
