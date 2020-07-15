package com.example.retrofittest.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofittest.R
import com.example.retrofittest.di.DaggerNetComponent
import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.repository.ImageUrlCallback

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
        dogsRe.getDogImageUrl(object : ImageUrlCallback {
            override fun setUrl(url: String) {
                setImage(url)
            }
        })
    }

    private fun setImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .into(dogImage)
    }
}