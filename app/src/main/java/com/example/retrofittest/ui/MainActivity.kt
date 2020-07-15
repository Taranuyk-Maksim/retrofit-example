package com.example.retrofittest.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofittest.R
import com.example.retrofittest.di.DaggerNetComponent
import com.example.retrofittest.repository.DogsRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var dogImage: ImageView
    private lateinit var getDogButton: Button
    private var dogsRe = DogsRepositoryImpl()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel(dogsRe)
        DaggerNetComponent.create().inject(dogsRe)
        setContentView(R.layout.activity_main)
        dogImage = findViewById(R.id.iv_dogs)
        getDogButton = findViewById(R.id.btn_get_dogs)
        getDogButton.setOnClickListener { onClick() }

    }

    private fun onClick() {

        viewModel.isConnect(this, object : MainViewModel.ConnectionCallBack {
            override fun isConnect(status: Boolean) {
                if (status) {
                    imageUpdate()
                } else {
                    Toast.makeText(this@MainActivity, "Error connection", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun imageUpdate() {
        viewModel.getUrl(object : MainViewModel.ImageCallBack {
            override fun onComplete(url: String) {
                setImage(url)
            }

            override fun onError() {
                Toast.makeText(this@MainActivity, "Error load image", Toast.LENGTH_SHORT).show()

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