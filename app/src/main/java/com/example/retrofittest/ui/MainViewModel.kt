package com.example.retrofittest.ui

import androidx.lifecycle.ViewModel
import com.example.retrofittest.repository.DogsApiRepository
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var repository : DogsApiRepository

    fun buttonClick () {
        repository.getDogImageUrl()
    }

}