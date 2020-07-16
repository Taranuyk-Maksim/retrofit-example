package com.example.retrofittest.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.retrofittest.R
import com.example.retrofittest.di.components.DaggerNetComponent
import com.example.retrofittest.repository.DogsRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var dogImage: ImageView
    private lateinit var getDogButton: Button
    private var dogsRe = DogsRepositoryImpl()
    lateinit var viewModel: MainViewModel
    lateinit var process: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initViewModel()
        initDagger()
        getDogButton.setOnClickListener { onClick() }


    }

    fun initView() {
        dogImage = findViewById(R.id.iv_dogs)
        getDogButton = findViewById(R.id.btn_get_dogs)
        process = findViewById(R.id.progressBar)
    }
    fun initDagger(){
        DaggerNetComponent.create().inject(dogsRe)
    }

    fun initViewModel() {
        viewModel = MainViewModel(dogsRe)
        viewModel.liveData.observe(this, Observer {
            if (it == null) makeToast("Error load image")
            else setImage(it)
        })
    }

    private fun onClick() {

        viewModel.isConnect(this, object : MainViewModel.ConnectionCallBack {
            override fun isConnect(status: Boolean) {
                if (status) {
                    setProgressVisibility()
                } else {
                    makeToast("Connection error")
                }
            }

            override fun onError(error: String) {
                makeToast(error)
            }
        })
    }

    private fun setProgressVisibility() {
        viewModel.getUrl(object : MainViewModel.ProgressBarrCallback {
            override fun showProgress(boolean: Boolean) {
                if (boolean) {
                    process.visibility = View.VISIBLE
                } else {
                    process.visibility = View.GONE
                }
            }
        })
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .into(dogImage)
    }
}