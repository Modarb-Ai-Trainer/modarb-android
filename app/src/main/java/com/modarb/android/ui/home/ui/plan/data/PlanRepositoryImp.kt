package com.modarb.android.ui.home.ui.plan.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.plan.ExercisesPagingSource
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse
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
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, PlanPageResponse::class.java)
                ApiResult.Error(parsedError)
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
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, CustomWorkoutResponse::class.java)
                ApiResult.Error(parsedError)

            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getExercises(
        token: String, filter: String, page: Int, limit: Int
    ): ApiResult<ExercisesResponse> {
        return try {
            val response = apiService.getExercises(token, filter, page, limit)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, ExercisesResponse::class.java)
                ApiResult.Error(parsedError)

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
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, ExercisesResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun createCustomWorkout(
        token: String, createCustomWorkoutRequest: CreateCustomWorkoutRequest
    ): ApiResult<CreateCustomWorkoutResponse> {
        return try {
            val response = apiService.createCustomWorkout(token, createCustomWorkoutRequest)

            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError =
                    Gson().fromJson(errorResponse, CreateCustomWorkoutResponse::class.java)
                Log.e("customworkouterror", parsedError.errors?.get(0).toString())
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            Log.e("CreateCustomError", e.message.toString())
            ApiResult.Failure(e)
        }
    }


    fun getExercisesPagingData(token: String, filter: String): Flow<PagingData<Data>> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ExercisesPagingSource(apiService, token, filter) }).flow
    }


}
