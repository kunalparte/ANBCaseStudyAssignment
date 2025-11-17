package com.example.anbcasestudyassigment.book.data.mapper

import android.util.Log
import com.example.anbcasestudyassigment.book.data.dto.BookDTO
import com.example.anbcasestudyassigment.books.domain.Book
import com.google.gson.Gson


fun BookDTO.toBook() : Book {
    val book = Book(
        id = id.toString(),
        title = title,
        imageUrl = if (cover_edition_key != null) {
            "https://covers.openlibrary.org/b/olid/${cover_edition_key}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${cover_i}-L.jpg"
        },
        authors = author_name ?: emptyList(),
        description = description,
        languages = languages ?: emptyList(),
        firstPublishYear = first_publish_year.toString(),
        averageRating = ratings_average,
        ratingCount = ratings_count,
        numPages = number_of_pages_median,
        numEditions = edition_count ?: 0
    )

    return book
}