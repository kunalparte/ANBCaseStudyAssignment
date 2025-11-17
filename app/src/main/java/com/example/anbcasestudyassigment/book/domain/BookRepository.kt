package com.example.anbcasestudyassigment.books.domain

import com.example.anbcasestudyassigment.book.data.dto.BookListDTO
import com.example.anbcasestudyassigment.core.data.ApiResult
import kotlinx.coroutines.flow.StateFlow


interface BookRepository {
    suspend fun getBookList(queryMap: Map<String, Any>)
    val apiResultState: StateFlow<ApiResult<BookListDTO>>
}