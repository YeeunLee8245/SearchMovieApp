package kr.co.yeeun.lee.demoi.searchmovieapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.ApiClient
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.ApiService
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.HeadInterceptor
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.MovieDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(applicationContext: Application): Context { // 싱글톤 컴포넌트는 종속 항목인 applicationContext을 제공함
        return applicationContext
    }

    @Singleton
    @Provides
    fun provideApiService(apiClient: ApiClient): ApiService {
        return apiClient.retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDataStore(context: Context, apiService: ApiService): MovieDataStore {
        return MovieDataStore(context, apiService)
    }

    @Singleton
    @Provides
    fun provideHeadInterceptor(context: Context): HeadInterceptor = HeadInterceptor(context)

    @Singleton
    @Provides
    fun provideApiClient(context: Context, headInterceptor: HeadInterceptor): ApiClient {
        return ApiClient(context, headInterceptor)
    }

}