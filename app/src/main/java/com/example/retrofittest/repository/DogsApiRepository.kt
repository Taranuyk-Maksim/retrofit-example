package com.example.retrofittest.repository

interface DogsApiRepository {

    fun getDogImageUrl(url : ImageUrlCallback)
}