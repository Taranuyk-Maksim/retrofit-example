package com.example.retrofittest.di

import com.example.retrofittest.repository.DogsRepositoryImpl
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetModule::class])
@Singleton
interface NetComponent {
    fun inject(app: DogsRepositoryImpl)
}