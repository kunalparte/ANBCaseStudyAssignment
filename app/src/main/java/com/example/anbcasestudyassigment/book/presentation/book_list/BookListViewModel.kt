package com.example.anbcasestudyassigment.book.presentation.book_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anbcasestudyassigment.book.domain.usecase.GetBookUseCase
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import com.example.anbcasestudyassigment.utils.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCase: GetBookUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
    private val userEvents = MutableSharedFlow<BookListScreenActions>()
    private val _bookListState = MutableStateFlow(BookListState(emptyList(), 0, ""))
    val bookListState: StateFlow<BookListState> = _bookListState

    private val _bookListScreenEvents = MutableSharedFlow<BookListScreenEvents>()
    val bookListScreenEvents  = _bookListScreenEvents

    init {
        getBookListData(mapOf(
            "page" to 1,
            "limit" to 20
        ))

        handleAction()
    }
    fun onAction(action: BookListScreenActions) {
        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            userEvents.emit(action)
        }
    }

    private fun handleAction(){
        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            userEvents.collect {
                when (it) {
                    is BookListScreenActions.OnBookClicked -> {
                        _bookListScreenEvents.emit(BookListScreenEvents.navigateToBookDetails(it.book))

                    }
                    is BookListScreenActions.OnBookListScrolledToPaginate -> {
                        getBookListData(queryMap = mapOf(
                            "page" to it.pageCount,
                            "limit" to 20
                        ))
                    }

                    is BookListScreenActions.onRefresh -> {
                        getBookListData(mapOf(
                            "page" to 1,
                            "limit" to 20
                        ))

                    }
                }
            }
        }
    }

    fun getBookListData(queryMap: Map<String, Any>) {
        viewModelScope.launch(coroutineDispatcherProvider.ioDispatcher) {
            _bookListState.update {
                it.copy(
                    isLoading  = true
                )
            }
            bookUseCase.getBooksData(queryMap)
            bookUseCase.bookState.value.let{ result ->
                _bookListState.update {
                    it.copy(
                        books = if(_bookListState.value.page == 0 ) result.books else _bookListState.value.books + result.books ,
                        page = if (_bookListState.value.books.isNotEmpty()) {
                            _bookListState.value.books.size / 20
                        } else {
                            0
                        },
                        error = result.error,
                        isLoading = false
                    )
                }
            }
        }
    }
}