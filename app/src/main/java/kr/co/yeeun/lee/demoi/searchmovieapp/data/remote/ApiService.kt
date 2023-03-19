package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import kr.co.yeeun.lee.demoi.searchmovieapp.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/search/movie.json")
    suspend fun getMovieResponse(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int
    ): Response<MovieResponse>
}