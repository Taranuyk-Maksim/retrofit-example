package com.example.retrofittest.di.modules

import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.ui.MainActivity
import com.example.retrofittest.ui.MainViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val BASE_URL = "https://dog.ceo/"

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideNetUtils(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideRepository() : DogsRepositoryImpl{
        return DogsRepositoryImpl()
    }
    @Provides
    fun provideViewModel(repository: DogsRepositoryImpl) : MainViewModel {
        return MainViewModel(repository)
    }
}