package com.modarb.android.ui.home.ui.plan.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.plan.ExercisesPagingSource
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import kotlinx.coroutines.flow.Flow

class PlanRepositoryImp(private val apiService: ApiService) : MyPlanRepository {

    override suspend fun getMyPlanPage(
        workoutId: String, token: String
    ): ApiResult<PlanPageResponse> {

        return try {
            val response = apiService.getPlanPage(workoutId, token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as PlanPageResponse)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getCustomWorkouts(token: String): ApiResult<CustomWorkoutResponse> {
        return try {
            val response = apiService.getCustomWorkouts(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as CustomWorkoutResponse)

            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getExercises(
        token: String,
        filter: String,
        page: Int,
        limit: Int
    ): ApiResult<ExercisesResponse> {
        return try {
            val response = apiService.getExercises(token, filter, page, limit)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as ExercisesResponse)

            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getSearchExercises(
        token: String, searchTerm: String, filter: String
    ): ApiResult<ExercisesResponse> {
        return try {
            val response = apiService.getExercisesSearch(token, searchTerm, filter)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as ExercisesResponse)

            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    fun getExercisesPagingData(token: String, filter: String): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ExercisesPagingSource(apiService, token, filter) }
        ).flow
    }



}
