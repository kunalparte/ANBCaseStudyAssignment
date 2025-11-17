package com.example.anbcasestudyassigment

import com.example.anbcasestudyassigment.book.data.dto.BookDTO
import com.example.anbcasestudyassigment.book.data.dto.BookListDTO
import com.example.anbcasestudyassigment.book.domain.usecase.GetBookUseCase
import com.example.anbcasestudyassigment.book.presentation.book_list.BookListViewModel
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.books.domain.BookRepository
import com.example.anbcasestudyassigment.core.data.ApiResult
import com.example.anbcasestudyassigment.utils.TestDispatcher
import io.mockk.MockKStubScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest2 {

    val bookRepository : BookRepository = mockk()

    lateinit var bookViewModel : BookListViewModel


//    val testDispatcher = TestDispatcher(dispatcher)

    @Before
    fun setup(){

    }

    @Test
    fun testGetBookListDataFailure () = runTest{
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val testDispatcher = TestDispatcher(dispatcher)
        val getBookUseCase = GetBookUseCase(bookRepository, testDispatcher)
        bookViewModel = BookListViewModel(getBookUseCase, testDispatcher)


        val result = ApiResult.ErrorResult(error = "Something Went Wrong")

        val _mockedFailedResultState = MutableStateFlow<ApiResult<Nothing>>(ApiResult.Loading(loading = true))

        every { bookRepository.apiResultState }.returns(_mockedFailedResultState)

        coEvery{ bookRepository.getBookList(any())}.coAnswers {
            _mockedFailedResultState.value = result
        }

        bookViewModel.getBookListData(mapOf("page" to 1, "limit" to 20))
        testScheduler.advanceUntilIdle()

        val testResult =  bookViewModel.bookListState.value.error
        assertEquals( result.error, bookViewModel.bookListState.value.error)

    }

    @Test
    fun testGetBookListSuccess() = runTest {
        //Runtime Environment SetUp
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val testDispatcher = TestDispatcher(dispatcher)

        //ViewModel and GetBooksUseCase Initialization
        val getBookUseCase = GetBookUseCase(bookRepository, testDispatcher)
        bookViewModel = BookListViewModel(getBookUseCase, testDispatcher)

        //mocked State
        val mockedSuccessState = MutableStateFlow<ApiResult<BookListDTO>>(ApiResult.Loading(false))

        //Expected Result
        val expectedResult = ApiResult.SuccessResult(data = returnMockedBooks())

        every { bookRepository.apiResultState }.returns(  mockedSuccessState)

        coEvery { bookRepository.getBookList(any()) }.coAnswers {
            mockedSuccessState.value = expectedResult
        }

        //Actual Viemodel call
        bookViewModel.getBookListData(mapOf("page" to 1, "title" to "marvel", "limit" to 20 ))

        //Makes sure all the remaining coroutines are executed
        testScheduler.advanceUntilIdle()

        assertTrue { bookViewModel.bookListState.value.books.size == expectedResult.data.results.size}
        assertEquals(expectedResult.data.results[0].title,bookViewModel.bookListState.value.books[0].title)
    }


    fun returnMockedBooks(): BookListDTO{
        var i = 0
        var bookList = mutableListOf<BookDTO>()
        while (i < 10){
            bookList.add(
                BookDTO(
                    id = i,
                    title = "BookVersion $i",
                    languages = listOf("$i BookLanguage 1", "$i BookLanguage 2"),
                    cover_i = i,
                    authorKeys = listOf("$i BookKey 1", "$i BookKey 2"),
                    author_name = listOf("$i BookAuthor Name 1", "$i BookAuthor Name 2"),
                    cover_edition_key = "key_$i",
                    first_publish_year = 2002,
                    ratings_average = 5.0,
                    ratings_count = 100+i


                )
            )
            i++
        }
        return BookListDTO(
            results = bookList
        )
    }


}

