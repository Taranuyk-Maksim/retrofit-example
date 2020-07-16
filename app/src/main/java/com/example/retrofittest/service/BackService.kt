package com.example.retrofittest.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.retrofittest.repository.DogsApiRepository
import com.example.retrofittest.repository.DogsRepositoryImpl
import javax.inject.Inject

private const val TAG = "BackService"

class BackService : Service() {

    lateinit var repository: DogsApiRepository

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: $repository")
    }

    fun setRepository(repository: DogsRepositoryImpl) {
        this.repository = repository
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}