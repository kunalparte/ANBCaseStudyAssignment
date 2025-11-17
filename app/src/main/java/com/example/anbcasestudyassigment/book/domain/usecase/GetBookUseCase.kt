package com.example.anbcasestudyassigment.book.domain.usecase

import android.R
import com.example.anbcasestudyassigment.book.data.mapper.toBook
import com.example.anbcasestudyassigment.book.presentation.book_list.BookListState
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import com.example.anbcasestudyassigment.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBookUseCase @Inject constructor(
    private val repository: BookRepository,
    private val dispatcher : CoroutineDispatcherProvider
) {
    private var _bookState = MutableStateFlow(BookListState(emptyList(), 0, ""))
    val bookState = _bookState

    suspend fun getBooksData(queryMap: Map<String, Any>){
//        withContext(dispatcher.ioDispatcher){
            repository.getBookList(queryMap)
            val responseState = repository.apiResultState.value
            when(responseState){
                is ApiResult.ErrorResult -> {
                    bookState.update {
                        it.copy(
                            books = emptyList(),
                            page = 0,
                            error = responseState.error
                        )
                    }
                }

                is ApiResult.SuccessResult -> {
                    val booksList = mutableListOf<Book>()
                    try {
                        responseState.data.results.forEach {
                            booksList.add(it.toBook())
                        }
                    }catch (e: Exception){
                        print(e)
                    }
                    bookState.update {
                        it.copy(
                            books = booksList,
                            error = ""
                        )
                    }
                }

                else -> {

                }
            }
//        }
    }
}