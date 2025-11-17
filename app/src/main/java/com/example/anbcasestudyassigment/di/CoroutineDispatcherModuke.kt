package com.example.anbcasestudyassigment.di

import com.example.anbcasestudyassigment.utils.CoroutineDispatcherProvider
import com.example.anbcasestudyassigment.utils.ProdCoroutineDispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineDispatcherModuke {

    @Binds
    abstract fun providesCoroutineDispatcher(coroutineDispatcher: ProdCoroutineDispatcher): CoroutineDispatcherProvider
}