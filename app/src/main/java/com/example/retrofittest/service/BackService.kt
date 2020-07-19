package com.example.retrofittest.service

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.retrofittest.App

import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.ui.ARRAY_URLS
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "SERIALISE"
private const val SHARED_PREFS: String = "sharedPrefs"


class BackService : Service() {

    @Inject
    lateinit var repository: DogsRepositoryImpl

    private var urlList = arrayListOf<String>()
    var disposable: Disposable? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun getUrlFromRx() {
        disposable = repository.getDogImageUrl()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.message }
            .subscribe({
                createList(it)
            }, {

            })
    }

    private fun createList(url: String) {
        urlList.add(url)
    }

    private fun saveData(listUrls: ArrayList<String>) {
        val editor = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val list = gson.toJson(listUrls)
        editor.putString(ARRAY_URLS, list)
        editor.apply()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        for (i in 0..10) {
            getUrlFromRx()
        }
        saveData(urlList)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        App.dager.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}