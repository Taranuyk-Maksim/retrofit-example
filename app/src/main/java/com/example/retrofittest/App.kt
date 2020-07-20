package com.example.retrofittest

import android.app.Application
import android.app.Service
import com.example.retrofittest.di.components.DaggerNetComponent
import com.example.retrofittest.service.BackService
import com.example.retrofittest.ui.MainActivity

object App {
    val dagger = DaggerNetComponent.builder().build()
}