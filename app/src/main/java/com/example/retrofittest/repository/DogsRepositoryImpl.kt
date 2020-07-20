package com.example.retrofittest.repository

import com.example.retrofittest.App
import com.example.retrofittest.net.Model
import com.example.retrofittest.net.RandomDogApi
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(var retrofit: Retrofit): DogsApiRepository {


    override fun getDogImageUrl(): Single<Model> {

        val randomDogApi: RandomDogApi = retrofit.create(
            RandomDogApi::class.java
        )
        return randomDogApi.getData()
    }

    init {
        App.dagger.inject(this)
    }
}