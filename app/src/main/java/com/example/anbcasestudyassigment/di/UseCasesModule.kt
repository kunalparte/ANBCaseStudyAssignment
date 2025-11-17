package com.example.anbcasestudyassigment.di

import com.example.anbcasestudyassigment.book.domain.usecase.GetBookUseCase
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.utils.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent:: class)
class UseCasesModule {

    @Provides
    fun providesGetBooksUseCase(
        repository: BookRepository,
        dispatcherProvider: CoroutineDispatcherProvider
    ): GetBookUseCase{
        return GetBookUseCase(repository, dispatcherProvider)
    }
}