package com.example.anbcasestudyassigment.book.presentation.book_list

import com.example.anbcasestudyassigment.books.domain.Book

sealed interface BookListScreenEvents {
    data class navigateToBookDetails(val book : Book) : BookListScreenEvents
    data class showToast(val message : String) : BookListScreenEvents
}