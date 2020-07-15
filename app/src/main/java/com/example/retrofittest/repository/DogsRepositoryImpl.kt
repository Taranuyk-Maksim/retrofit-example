package com.example.retrofittest.repository

import com.example.retrofittest.net.RandomDogApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class DogsRepositoryImpl : DogsApiRepository {

    @Inject
    lateinit var retrofit: Retrofit

    override fun getDogImageUrl(urlCollback: ImageUrlCallback) {
        val randomDogApi: RandomDogApi = retrofit.create(
            RandomDogApi::class.java
        )
        randomDogApi.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                urlCollback.setUrl(result.message)
            }
    }
}