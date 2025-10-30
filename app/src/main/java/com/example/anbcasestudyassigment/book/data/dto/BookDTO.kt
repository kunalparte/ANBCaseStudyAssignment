package com.example.anbcasestudyassigment.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDTO(
    @SerialName("id")val id: Int,
    @SerialName("title")val title: String,
    @SerialName("language") val languages: List<String>? = null,
    @SerialName("cover_i") val cover_i: Int? = null,
    @SerialName("author_key") val authorKeys: List<String>? = null,
    @SerialName("author_name") val author_name: List<String>? = null,
    @SerialName("cover_edition_key") val cover_edition_key: String? = null,
    @SerialName("first_publish_year") val first_publish_year: Int? = null,
    @SerialName("ratings_average") val ratings_average: Double? = null,
    @SerialName("ratings_count") val ratings_count: Int? = null,
    @SerialName("number_of_pages_median") val number_of_pages_median: Int? = null,
    @SerialName("edition_count") val edition_count: Int? = null,
    @SerialName("description") val description: String? = null
)