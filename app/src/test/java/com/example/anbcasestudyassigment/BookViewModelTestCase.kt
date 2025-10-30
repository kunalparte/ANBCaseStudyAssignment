package com.example.anbcasestudyassigment

import com.example.anbcasestudyassigment.book.presentation.book_list.BookListViewModel
import com.example.anbcasestudyassigment.books.domain.Book


import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyMap
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest {

    private lateinit var repository: BookRepository
    private lateinit var viewModel: BookListViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mock(BookRepository::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- Helpers ---
    private fun makeBook(i: Int) = Book(
        id = i.toString(),
        title = "Book $i",
        imageUrl = "https://covers.openlibrary.org/b/olid/OL$i-L.jpg",
        authors = listOf("Author $i"),
        description = "desc $i",
        languages = listOf("en"),
        firstPublishYear = "200$i",
        averageRating = 4.0,
        ratingCount = 10,
        numPages = 123,
        numEditions = 1
    )

    // ---------------- Tests ----------------

    @Test
    fun `init loads books - success path with correct generics`() = runTest {
        // Arrange: repository exposes StateFlow<ApiResult<List<Book>>>
        val books = listOf(makeBook(1), makeBook(2))
        val flow = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.SuccessResult(books))

        whenever(repository.apiResultState).thenReturn(flow)
        // getBookList is a suspend function; we don't need it to do anything for this test
        doNothing().`when`(repository).getBookList(anyMap())

        // Act: creating VM triggers init { getBookListData(page=1,limit=20) }
        viewModel = BookListViewModel(repository)
        advanceUntilIdle()

        // Assert state
        val state = viewModel.bookListState.value
        assertEquals(2, state.books.size)
        assertEquals("", state.error)
        // previous list was empty -> page = 0 per ViewModel logic
        assertEquals(0, state.page)

        // Assert init query params were called
        val mapCaptor = argumentCaptor<Map<String, Any>>()
        verify(repository).getBookList(mapCaptor.capture())

        val q = mapCaptor.firstValue  // preferred in mockito-kotlin
        assertEquals(1, q["page"])
        assertEquals(20, q["limit"])

    }

    @Test
    fun `init handles error and exposes message`() = runTest {
        val error = "Network error"
        val flow = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.ErrorResult(error))

        whenever(repository.apiResultState).thenReturn(flow)
        doNothing().`when`(repository).getBookList(anyMap())

        viewModel = BookListViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.bookListState.value
        assertTrue(state.books.isEmpty())
        assertEquals(0, state.page)
        assertEquals(error, state.error)
    }

    @Test
    fun `pagination appends and updates page using previous size divided by 20`() = runTest {
        // Arrange first page = 20 items
        val firstPage = (1..20).map(::makeBook)
        val nextPage = (21..30).map(::makeBook) // 10 more

        val flow = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.SuccessResult(firstPage))
        whenever(repository.apiResultState).thenReturn(flow)
        doNothing().`when`(repository).getBookList(anyMap())

        // Act: initial load
        viewModel = BookListViewModel(repository)
        advanceUntilIdle()

        // Assert after first load
        var state = viewModel.bookListState.value
        assertEquals(20, state.books.size)
        assertEquals(0, state.page) // started from empty -> 0

        // Simulate repository returning a second page
        flow.value = ApiResult.SuccessResult(nextPage)

        // Trigger VM load for page 2
        viewModel.getBookListData(mapOf("page" to 2, "limit" to 20))
        advanceUntilIdle()

        // Assert appended and page derived from previous size (20/20 = 1)
        state = viewModel.bookListState.value
        assertEquals(30, state.books.size)
        assertEquals(1, state.page)
        assertEquals("", state.error)
    }
}
