package com.example.retrofittest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofittest.App
import com.example.retrofittest.R
import com.example.retrofittest.adapters.DogsAdapter
import com.example.retrofittest.di.components.DaggerNetComponent
import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.service.BackService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

const val ARRAY_URLS = "urlArray"
const val SHARED_PREFS: String = "sharedPrefs"


class MainActivity : AppCompatActivity() {

    private lateinit var dogImage: ImageView
    private lateinit var getDogButton: Button
    private lateinit var runServiceButton: Button
    private lateinit var showImages: Button


    lateinit var process: ProgressBar
    lateinit var recycler: RecyclerView

    @Inject
    lateinit var repo : DogsRepositoryImpl
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDagger()
        initView()
        initViewModel()
        initRecycler(arrayListOf(""))

        getDogButton.setOnClickListener {
            recycler.visibility = View.INVISIBLE
            dogImage.visibility = View.VISIBLE
            onClick()
        }

        showImages.setOnClickListener {
            dogImage.visibility = View.GONE
            recycler.visibility = View.VISIBLE

            initRecycler(getDataFromStorage(ARRAY_URLS))
        }

        runServiceButton.setOnClickListener {
            val intent = Intent(this, BackService::class.java)
            startService(intent)
        }

    }

    private fun initView() {
        showImages = findViewById(R.id.btn_show_images)
        dogImage = findViewById(R.id.iv_dogs)
        getDogButton = findViewById(R.id.btn_get_dogs)
        process = findViewById(R.id.progressBar)
        runServiceButton = findViewById(R.id.btn_run_service)

    }

    private fun initRecycler(list: ArrayList<String>) {
        recycler = findViewById(R.id.rv_all_photos)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = DogsAdapter(list)
    }

    private fun initDagger() {
        App.dager.inject(this)

    }

    private fun initViewModel() {
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

    private fun getDataFromStorage(key: String): ArrayList<String> {
        var list = arrayListOf<String>()
        val gsonString = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(key, "")

        val gson = Gson()
        if (gsonString != "") {
            val type: Type = object : TypeToken<List<String?>?>() {}.type
            list = gson.fromJson(gsonString, type)
        } else {
            makeToast("List is empty")
        }

        return list
    }

    private fun setImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .into(dogImage)
    }
}