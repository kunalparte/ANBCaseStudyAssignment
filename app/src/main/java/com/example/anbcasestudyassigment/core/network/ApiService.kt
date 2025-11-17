package com.example.anbcasestudyassigment.core.network

import com.example.anbcasestudyassigment.book.data.dto.BookListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET(ApiConstants.SEARCH_BOOKS)
    suspend fun getBookList(
        @Query("page") page: Int ,
        @Query("title") title: String = "fiction",
        @Query("limit") limit: Int = 20
    ): Response<BookListDTO>
}