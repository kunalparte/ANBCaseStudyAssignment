package com.example.anbcasestudyassigment.book.presentation.book_list

import com.example.anbcasestudyassigment.books.domain.Book


sealed interface BookListScreenActions {
    data class OnBookClicked(val book : Book) : BookListScreenActions
    data class OnBookListScrolledToPaginate(var pageCount : Int) : BookListScreenActions
}