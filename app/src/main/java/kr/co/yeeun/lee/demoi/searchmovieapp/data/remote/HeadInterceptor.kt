package kr.co.yeeun.lee.demoi.searchmovieapp.data.remote

import android.content.Context
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            addHeader("X-Naver-Client-Id", context.getString(R.string.movie_client_id))
            addHeader("X-Naver-Client-Secret", context.getString(R.string.movie_client_secrete))
        }.build()

        return chain.proceed(request)
    }
}