package com.example.anbcasestudyassigment.book.presentation.book_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _bookListState = MutableStateFlow(BookListState(emptyList(), 0, ""))
    val bookListState: StateFlow<BookListState> = _bookListState

    init {
        getBookListData(mapOf(
            "page" to 1,
            "limit" to 20
        ))
    }
    fun onAction(action: BookListScreenActions) {
        when (action) {
            is BookListScreenActions.OnBookClicked -> { /* TODO */ }
            is BookListScreenActions.OnBookListScrolledToPaginate -> { /* TODO */ }
        }
    }

    fun getBookListData(queryMap: Map<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBookList(queryMap)
            when (val result = repository.apiResultState.value) {
                is ApiResult.SuccessResult -> {
                    _bookListState.value = BookListState(
                        books = _bookListState.value.books + result.data,
                        page = if (_bookListState.value.books.isNotEmpty()) {
                            _bookListState.value.books.size / 20
                        } else {
                        0
                    },
                    error = "")
                }
                is ApiResult.ErrorResult -> {
                    _bookListState.value = BookListState(books = emptyList(), page = 0, error = result.error)
                }
            }
        }
    }
}