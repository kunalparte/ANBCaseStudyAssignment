package com.example.anbcasestudyassigment.di

import com.example.anbcasestudyassigment.book.data.repository.BookRepositoryImpl
import com.example.anbcasestudyassigment.books.domain.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

}