package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val context: Context,
    private val headInterceptor: HeadInterceptor
) {
    val retrofit get() = _retrofit
    private val baseURL = context.getString(R.string.movie_base_url)
    private val gson = GsonBuilder()
        .create()
    private val _retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson)) // GSON 처리
            .build()
    }

    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headInterceptor)  // 공통 헤더 추가
        .build()
}