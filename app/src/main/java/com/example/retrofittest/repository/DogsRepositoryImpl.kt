package com.example.retrofittest.repository

import com.example.retrofittest.App
import com.example.retrofittest.net.Model
import com.example.retrofittest.net.RandomDogApi
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(): DogsApiRepository {
    @Inject
    lateinit var retrofit: Retrofit

    override fun getDogImageUrl(): Single<Model> {
        App.dager.inject(this)
        val randomDogApi: RandomDogApi = retrofit.create(
            RandomDogApi::class.java
        )
        return randomDogApi.getData()
    }
}