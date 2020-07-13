package com.example.retrofittest.net

import retrofit2.Call
import retrofit2.http.GET

interface RandomDogApi {

    @GET("api/breeds/image/random")
    fun getData(): Call<Model>
}