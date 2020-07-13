package com.example.retrofittest.repository

import android.util.Log
import com.example.retrofittest.net.Model
import com.example.retrofittest.net.RandomDogApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class DogsRepositoryImpl : DogsApiRepository {

    @Inject
    lateinit var retrofit: Retrofit

    override fun getDogImageUrl() : String {
        val randomDogApi: RandomDogApi = retrofit.create(
            RandomDogApi::class.java
        )
        val call: Call<Model> = randomDogApi.getData()
        try {
            call.enqueue(object : Callback<Model> {
                override fun onResponse(call: Call<Model>, response: Response<Model>) {
                    response.body()?.message
                }

                override fun onFailure(call: Call<Model>, t: Throwable) {

                }
            })
        }catch (ex : Exception) {
            Log.d("tag", "getDogImageUrl: exeption ")
        }

       return ""
    }
}