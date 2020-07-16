package com.example.retrofittest.di.components

import com.example.retrofittest.di.modules.NetModule
import com.example.retrofittest.repository.DogsRepositoryImpl
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetModule::class])
@Singleton
interface NetComponent {
    fun inject(app: DogsRepositoryImpl)
}