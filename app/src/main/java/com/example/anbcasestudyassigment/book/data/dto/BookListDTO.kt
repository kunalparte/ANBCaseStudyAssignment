package com.example.anbcasestudyassigment.book.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BookListDTO(
    @SerializedName("docs") val results: List<BookDTO>
)
