package com.example.anbcasestudyassigment.book.presentation.book_list
import com.example.anbcasestudyassigment.books.domain.Book

data class BookListState(
    val books : List<Book>,
    val page : Int,
    val error : String
)
