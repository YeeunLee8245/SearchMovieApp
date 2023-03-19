package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import android.content.Context
import android.util.Log
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import kr.co.yeeun.lee.demoi.searchmovieapp.data.model.MovieResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataStore @Inject constructor(private val context: Context, private val apiService: ApiService) {

    suspend fun getMovieResponse(
        query: String,
        display: Int,
        start: Int
    ): MovieResponse? {
//        Log.d("응답 받기 시작", "$query $display $start")
        val response = apiService.getMovieResponse(query, display, start)
        val body = response.body()
//        Log.d("응답 코드 확인", response.code().toString())
//        Log.d("응답 바디 확인", response.body().toString())
//        Log.d("응답 메시지 확인", response.message())
        if (response.code() / 100 in 4..5) {
            throw java.lang.Exception(response.message())
        }
        response.errorBody()?.string()?.let {
            throw java.lang.Exception(context.getString(R.string.invalid_request))
        }
        return body
    }
}