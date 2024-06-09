package com.modarb.android.ui.home.ui.plan

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data

class ExercisesPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val filterName: String,
    private val filterVal: String
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val page = params.key ?: 0
        try {
            val response =
                apiService.getExercises(token, filterName, filterVal, page, params.loadSize)

            return LoadResult.Page(
                data = response.body()!!.data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.body()!!.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
