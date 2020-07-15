package com.example.retrofittest.net

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface RandomDogApi {

    @GET("api/breeds/image/random")
    fun getData(): Single<Model>
}