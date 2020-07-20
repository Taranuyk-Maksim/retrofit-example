package com.example.retrofittest.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.retrofittest.App
import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.ui.ARRAY_URLS
import com.example.retrofittest.ui.MainViewModel
import com.example.retrofittest.ui.SHARED_PREFS
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoadPhotoWorker(var appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    @Inject
    lateinit var repository: DogsRepositoryImpl
    private var disposable: Disposable? = null
    private var urlList = arrayListOf<String>()

    override fun doWork(): Result {
        while (true){
            getUrlFromRx()
            saveData(urlList)
            Thread.sleep(4000)
        }

       return Result.success()
    }

    init {
        App.dagger.inject(this)
    }
    private fun getUrlFromRx() {
        Log.d("Intent", "getUrlFromRx: run ")
        disposable = repository.getDogImageUrl()
            .subscribeOn(Schedulers.io())
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

    private fun saveData(listUrls: ArrayList<String>) {
        val editor = appContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val list = gson.toJson(listUrls)
        editor.putString(ARRAY_URLS, list)
        editor.apply()
    }
}