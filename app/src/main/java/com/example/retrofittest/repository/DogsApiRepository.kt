package com.example.retrofittest.repository

import com.example.retrofittest.net.Model
import io.reactivex.Single

interface DogsApiRepository {

    fun getDogImageUrl(): Single<Model>

}