package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataStore @Inject constructor(private val apiClient: ApiClient) {
    val api = apiClient.retrofit.create(ApiService::class.java)

//    suspend fun getMovieResponse(
//        query: String,
//        display: Int,
//        start: Int,
//        callback()
//    ) {
//        return try {
//            val response = api.getMovieResponse(query, display, start)
//        } catch (e: Exception) {
//            Log.e("응답 오류", e.toString())
//
//        }
//    }
}