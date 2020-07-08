package com.example.retrofittest

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofittest.net.Model
import com.example.retrofittest.net.RandomDogApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dog.ceo/"

class MainActivity : AppCompatActivity() {

    private lateinit var dogImage: ImageView
    private lateinit var getDogButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dogImage = findViewById(R.id.iv_dogs)
        getDogButton = findViewById(R.id.btn_get_dogs)

        getDogButton.setOnClickListener { onClick() }
    }

    private fun onClick() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        val randomDogApi: RandomDogApi = retrofit.create(RandomDogApi::class.java)
        val call: Call<Model> = randomDogApi.getData()

        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                val m: Model? = response.body()
                Glide
                    .with(this@MainActivity)
                    .load(m?.message)
                    .into(dogImage)
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {

            }
        })
    }
}