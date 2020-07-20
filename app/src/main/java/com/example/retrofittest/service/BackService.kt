package com.example.retrofittest.service

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.retrofittest.App
import com.example.retrofittest.net.RandomDogApi
import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.ui.ARRAY_URLS
import com.example.retrofittest.ui.SHARED_PREFS
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BackService : IntentService("BackService") {

    @Inject
    lateinit var repository: DogsRepositoryImpl

    private var urlList = arrayListOf<String>()
    var disposable: Disposable? = null

    private fun getUrlFromRx() {

        Log.d("Intent", "getUrlFromRx: run ")
        disposable = repository.getDogImageUrl()
            .subscribeOn(Schedulers.io())
           // .delay(3000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.message }
            .subscribe({
                Log.d("Intent", "getUrlFromRx: $it")
                createList(it)
            }, {
                Log.d("Intent", "Error")
            })
    }

    private fun createList(url: String) {
        urlList.add(url)
        saveData(urlList)
    }

    init {
        App.dagger.inject(this)
    }

    override fun onHandleIntent(p0: Intent?) {
        getUrlFromRx()
        Log.d("Intent", "onHandleIntent: list photos $urlList")
    }

    private fun saveData(listUrls: ArrayList<String>) {
        val editor = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val list = gson.toJson(listUrls)
        editor.putString(ARRAY_URLS, list)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}