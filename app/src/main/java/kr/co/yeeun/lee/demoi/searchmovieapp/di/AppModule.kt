package kr.co.yeeun.lee.demoi.searchmovieapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(applicationContext: Application): Context { // 싱글톤 컴포넌트는 종속 항목인 applicationContext을 제공함
        return applicationContext
    }
}