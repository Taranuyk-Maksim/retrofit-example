package com.example.retrofittest.di.components

import androidx.lifecycle.ViewModel
import com.example.retrofittest.di.modules.NetModule
import com.example.retrofittest.repository.DogsRepositoryImpl
import com.example.retrofittest.service.BackService
import com.example.retrofittest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetModule::class])
@Singleton
interface NetComponent {
    fun inject(app: DogsRepositoryImpl)
    fun inject(app: MainActivity)
    fun inject(app: BackService)
    fun inject(app: ViewModel)

}