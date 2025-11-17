package com.example.anbcasestudyassigment.di

import com.example.anbcasestudyassigment.core.network.ApiClient
import com.example.anbcasestudyassigment.core.network.ApiClientImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  ApiClientModule {


    @Binds
    @Singleton
    abstract fun bindApiClient(
        apiClientImpl: ApiClientImpl
    ): ApiClient

}