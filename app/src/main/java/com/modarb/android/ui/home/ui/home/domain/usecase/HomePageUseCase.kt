package com.modarb.android.ui.home.ui.home.domain.usecase

import com.modarb.android.network.Result
import com.modarb.android.ui.home.ui.home.domain.HomeRepository
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse

class HomePageUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(token: String): Result<HomePageResponse> {
        return homeRepository.getHomePage(token)
    }
}