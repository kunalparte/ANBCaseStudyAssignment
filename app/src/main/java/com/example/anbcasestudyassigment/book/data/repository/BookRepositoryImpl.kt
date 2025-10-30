package com.example.anbcasestudyassigment.book.data.repository

import com.example.anbcasestudyassigment.book.data.dto.BookDTO
import com.example.anbcasestudyassigment.book.data.dto.BookListDTO
import com.example.anbcasestudyassigment.book.data.mapper.toBook
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import com.example.anbcasestudyassigment.core.network.ApiClient
import com.example.anbcasestudyassigment.core.network.ApiConstants
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
) : BookRepository {

    private val _apiResultState =
        MutableStateFlow<ApiResult<List<Book>>>(ApiResult.SuccessResult(emptyList()))
    override val apiResultState: StateFlow<ApiResult<List<Book>>> = _apiResultState

    override suspend fun getBookList(queryMap: Map<String, Any>) {
        val listType = object : TypeToken<BookListDTO>() {}.type
        apiClient.getDataFromApiCall(
            endPointUrl = ApiConstants.SEARCH_BOOKS,
            queryParamMap = HashMap(queryMap),     // avoid unsafe cast
            responseType = listType,
            onSuccess = { bookListData: BookListDTO ->
                val books = bookListData.results.map { it.toBook() }
                _apiResultState.value = ApiResult.SuccessResult(books) // <-- Kotlin style (no <>)
            },
            onError = { error: String ->
                _apiResultState.value = ApiResult.ErrorResult(error)
            }
        )
    }
}