package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.yeeun.lee.demoi.searchmovieapp.data.model.Movie
import kr.co.yeeun.lee.demoi.searchmovieapp.util.Constant

class MoviePagingSource(
    private val query: String,
    private val dataStore: MovieDataStore
) : PagingSource<Int, Movie>() {
    private var responseTotal = 0

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            var key: Int = 1
            val start: Int
            if (params.key == null || params.key == 1) {
                responseTotal = 0
                start = Constant.START_PAGING_KEY
            } else {
                key = params.key ?: Constant.START_PAGING_KEY
                start = (key - 1) * Constant.PAGING_SIZE + 1
            }

            val response = dataStore.getMovieResponse(query, Constant.PAGING_SIZE, start)
            val data = response?.items

            LoadResult.Page(
                data = data ?: emptyList(),
                prevKey = null,
                nextKey = if ((key * Constant.PAGING_SIZE) > (response?.total ?: 0)) null else key.plus(1)
            )
        } catch (e: java.lang.Exception) {
            Log.d("응답 오류", "${e}")
            LoadResult.Error(e)
        }
    }
}