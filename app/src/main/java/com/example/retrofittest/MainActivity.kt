package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofittest.di.DaggerNetComponent
import com.example.retrofittest.di.NetModule
import com.example.retrofittest.net.Model
import com.example.retrofittest.net.RandomDogApi
import com.example.retrofittest.repository.DogsRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var dogImage: ImageView
    private lateinit var getDogButton: Button
    private val dogsRe = DogsRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNetComponent.create().inject(dogsRe)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dogImage = findViewById(R.id.iv_dogs)
        getDogButton = findViewById(R.id.btn_get_dogs)
        getDogButton.setOnClickListener { onClick() }

    }

    private fun onClick() {
        setImage(dogsRe.getDogImageUrl())
    }

    private fun setImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .into(dogImage)
    }
}